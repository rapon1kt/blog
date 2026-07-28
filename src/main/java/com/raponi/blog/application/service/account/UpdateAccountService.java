package com.raponi.blog.application.service.account;

import java.io.IOException;
import java.time.Instant;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.application.usecase.account.UpdateAccountCommand;
import com.raponi.blog.application.usecase.account.UpdateAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.ImageValidationResponse;
import com.raponi.blog.application.validators.ImageValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.InvalidParamException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;

@Service
public class UpdateAccountService implements UpdateAccountUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final ImageValidatorService imageValidatorService;
  private final GridFsTemplate gridFsTemplate;

  public UpdateAccountService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService,
      ImageValidatorService imageValidatorService, GridFsTemplate gridFsTemplate) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.imageValidatorService = imageValidatorService;
    this.gridFsTemplate = gridFsTemplate;
  }

  @Override
  public Account handle(String accountId, UpdateAccountCommand command, MultipartFile image)
      throws IOException {
    boolean isAccountValid = this.accountValidatorService.verifyAccountWithAccountId(accountId);

    if (!isAccountValid)
      throw new AccessDeniedException("You don't have permission to do this.");

    Account accountToUpdate = this.accountRepository.findById(accountId).get();

    String imageId = "";
    if (image != null) {
      imageId = gridFsTemplate.store(
          image.getInputStream(),
          image.getOriginalFilename(),
          image.getContentType()).toHexString();
    }

    accountToUpdate.setPicture(imageId == "" ? accountToUpdate.getPicture() : validateImage(image, imageId));
    if (command != null) {
      accountToUpdate.setUsername(command.username() == null ? accountToUpdate.getUsername()
          : validateUsername(command.username()));

      accountToUpdate.setDescription(command.description() == null ? accountToUpdate.getDescription()
          : command.description());
    }
    accountToUpdate.setModifiedAt(Instant.now());
    Account savedAccount = this.accountRepository.save(accountToUpdate);

    return savedAccount;
  }

  private String validateUsername(String username) {
    if (this.accountRepository.existsByUsername(username)) {
      throw new InvalidParamException("Username already registred.");
    } else if (username.length() < 3) {
      throw new InvalidParamException("The username must be at least 3 characters long.");
    }
    return username;
  }

  private String validateImage(MultipartFile file, String imageId) {
    ImageValidationResponse validationResponse = this.imageValidatorService.isValid(file);
    if (!validationResponse.isValid()) {
      throw new IllegalArgumentException(validationResponse.message());
    }
    return imageId;
  }

}
