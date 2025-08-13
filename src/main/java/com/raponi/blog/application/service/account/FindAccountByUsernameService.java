package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByUsernameUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class FindAccountByUsernameService implements FindAccountByUsernameUseCase {

  private final AccountRepository accountRepository;

  public FindAccountByUsernameService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Optional<Account> handle(String username) {
    Optional<Account> account = this.accountRepository.findByUsername(username);
    if (account.isPresent()) {
      return account;
    }
    throw new AccountNotFound("username equals " + username);
  }

}
