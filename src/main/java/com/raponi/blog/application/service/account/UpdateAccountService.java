package com.raponi.blog.application.service.account;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.UpdateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class UpdateAccountService implements UpdateAccountUseCase {

  private final AccountRepository accountRepository;

  public UpdateAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account handle(String accountId, Account newAccountInfos) {
    Optional<Account> accountToUpdate = this.accountRepository.findById(accountId);
    if (accountToUpdate.isPresent()) {
      Account updated = new Account(
          accountToUpdate.get().id(),
          newAccountInfos.email(),
          newAccountInfos.username(),
          accountToUpdate.get().password(),
          accountToUpdate.get().createdAt(),
          Instant.now());
      return this.accountRepository.save(updated);
    }
    return null;
  }

}
