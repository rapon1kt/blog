package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountValidatorService accountValidatorService;
  private final AccountRepository accountRepository;

  public FindAccountByIdService(AccountValidatorService accountValidatorService, AccountRepository accountRepository) {
    this.accountValidatorService = accountValidatorService;
    this.accountRepository = accountRepository;
  }

  @Override
  public Account handle(String accountId) {
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findById(accountId).get();
    return account;
  }

}
