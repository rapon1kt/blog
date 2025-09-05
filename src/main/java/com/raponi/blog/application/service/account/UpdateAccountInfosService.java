package com.raponi.blog.application.service.account;

import java.io.IOException;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.ImageValidationResponse;
import com.raponi.blog.application.validators.ImageValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.UpdateAccountInfosUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.UpdateAccountInfosRequestDTO;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class UpdateAccountInfosService implements UpdateAccountInfosUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final ImageValidatorService imageValidatorService;
  private final AccountMapper accountMapper;
  private final GridFsTemplate gridFsTemplate;

  public UpdateAccountInfosService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService,
      ImageValidatorService imageValidatorService,
      AccountMapper accountMapper, GridFsTemplate gridFsTemplate) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.imageValidatorService = imageValidatorService;
    this.accountMapper = accountMapper;
    this.gridFsTemplate = gridFsTemplate;
  }

  @Override
  public AccountResponseDTO handle(String accountId, UpdateAccountInfosRequestDTO requestDTO, MultipartFile image)
      throws IOException {
    Account accountToUpdate = this.accountValidatorService.getAccountByAccountId(accountId);

    String imageId = gridFsTemplate.store(
        image.getInputStream(),
        image.getOriginalFilename(),
        image.getContentType()).toHexString();

    Account accountUpdated = accountToUpdate.updateAccountInfos(
        requestDTO.getUsername().isBlank() ? accountToUpdate.username() : validateUsername(requestDTO.getUsername()),
        image.isEmpty() ? accountToUpdate.picture() : validateImage(image, imageId),
        requestDTO.getProfileDescription());

    AccountResponseDTO responseAccount = this.accountMapper.toResponse(accountUpdated);
    this.accountRepository.save(accountUpdated);

    return responseAccount;
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
