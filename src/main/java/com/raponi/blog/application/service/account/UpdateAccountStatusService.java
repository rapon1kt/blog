package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class UpdateAccountStatusService implements ChangeStatusUseCase {

  private AccountRepository accountRepository;
  private AccountValidatorService accountValidatorService;

  public UpdateAccountStatusService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String accountId) {
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (!authorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findById(accountId).get();
    return this.accountRepository.save(account.changeStatus()).toResponseBody();
  }
}
