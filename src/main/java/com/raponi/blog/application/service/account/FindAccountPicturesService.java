package com.raponi.blog.application.service.account;

import java.io.IOException;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountPicturesUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

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
  public byte[] handle(String accountId) throws IOException {
    Optional<Account> account = this.accountRepository.findById(accountId);
    boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(account);
    if (!verifiedAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    GridFSFile image = this.gridFsTemplate
        .findOne(new Query(Criteria.where("_id").is(new ObjectId(account.get().picture()))));
    GridFsResource imageResource = gridFsTemplate.getResource(image);
    return imageResource.getInputStream().readAllBytes();
  }
}
