package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.UpdateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class UpdateAccountService implements UpdateAccountUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public UpdateAccountService(AccountRepository accountRepository, AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String accountId, String newUsername) {
    Account accountToUpdate = this.accountValidatorService.getAccountByAccountId(accountId);
    Account accountUpdated = accountToUpdate.update(newUsername);

    this.accountRepository.save(accountUpdated);
    return accountUpdated.toResponseBody();
  }
}
