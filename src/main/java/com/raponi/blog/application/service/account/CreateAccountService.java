package com.raponi.blog.application.service.account;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.usecase.account.CreateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class CreateAccountService implements CreateAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoderService;

  public CreateAccountService(AccountRepository accountRepository, PasswordEncoderService passwordEncoderService) {
    this.accountRepository = accountRepository;
    this.passwordEncoderService = passwordEncoderService;
  }

  @Override
  public Account handle(Account newAccount) {
    String hashedPassword = passwordEncoderService.encode(newAccount.password());

    Account accountToSave = new Account(
        newAccount.id(),
        newAccount.email(),
        newAccount.username(),
        hashedPassword,
        Instant.now(),
        Instant.now());
    return this.accountRepository.save(accountToSave);
  }

}
