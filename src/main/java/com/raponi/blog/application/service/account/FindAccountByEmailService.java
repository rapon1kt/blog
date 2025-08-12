package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByEmailUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class FindAccountByEmailService implements FindAccountByEmailUseCase {

  private final AccountRepository accountRepository;

  public FindAccountByEmailService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }
  
  @Override
  public Optional<Account> handle(String email) {
    return this.accountRepository.findByEmail(email);
  }

}
