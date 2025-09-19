package com.raponi.blog.application.service.account;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.raponi.blog.application.usecase.account.FindAccountPicturesUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class FindAccountPicturesService implements FindAccountPicturesUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final GridFsTemplate gridFsTemplate;

  public FindAccountPicturesService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService, GridFsTemplate gridFsTemplate) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.gridFsTemplate = gridFsTemplate;
  }

  @Override
  public byte[] handle(String username) throws IOException {
    String accountId = this.accountValidatorService.verifyAccountWithUsernameAndReturnId(username);
    if (accountId.equals(null))
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean isViwerBlocked = this.accountValidatorService.isBlocked(accountId);
    if (isViwerBlocked)
      return null;
    Account account = this.accountRepository.findByUsername(username).get();
    if (account.getPicture().isEmpty() || account.getPicture().equals(null))
      throw new ResourceNotFoundException("This account don't have a profile picture.");

    GridFSFile image = this.gridFsTemplate
        .findOne(new Query(Criteria.where("_id").is(new ObjectId(account.getPicture()))));
    GridFsResource imageResource = gridFsTemplate.getResource(image);
    return imageResource.getInputStream().readAllBytes();
  }
}
