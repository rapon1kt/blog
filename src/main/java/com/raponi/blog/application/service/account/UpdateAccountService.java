package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.UpdateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class UpdateAccountService implements UpdateAccountUseCase {

  private final AccountRepository accountRepository;

  public UpdateAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Http.ResponseBody handle(String accountId, String newUsername) {
    Optional<Account> accountToUpdate = this.accountRepository.findById(accountId);
    if (accountToUpdate.isPresent()) {
      Account accountUpdated = accountToUpdate.get().update(newUsername);
      this.accountRepository.save(accountUpdated);
      return accountUpdated.toResponseBody();
    }
    throw new AccountNotFound("id equals " + accountId);
  }
}
