package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountRepository accountRepository;

  public FindAccountByIdService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Optional<Account> handle(String accountId) {
    return this.accountRepository.findById(accountId);
  }

}
