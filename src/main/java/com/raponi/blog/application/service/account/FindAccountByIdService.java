package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountRepository accountRepository;

  public FindAccountByIdService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Optional<Account> handle(String accountId) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    if (account.isPresent()) {
      return account;
    }
    throw new AccountNotFound("id equals " + accountId);
  }

}
