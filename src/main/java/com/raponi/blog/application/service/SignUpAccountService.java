package com.raponi.blog.application.service;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.command.SignUpAccountCommand;
import com.raponi.blog.application.usecase.SignUpAccountUseCase;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.AccountRepository;
import com.raponi.blog.domain.port.PasswordEncoderPort;

@Service
public class SignUpAccountService implements SignUpAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderPort passwordEncoder;

  public SignUpAccountService(AccountRepository accountRepository, PasswordEncoderPort passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Account handle(SignUpAccountCommand command) {
    if (isEmailOrUsernameInUse(command.email(), command.username())) {
      throw new InvalidCredentialsException("Email and/or Username already in use.");
    }
    String encodedPassword = passwordEncoder.encode(command.password());
    Account newAccount = new Account(command.email(), command.username(), encodedPassword);
    return accountRepository.save(newAccount);
  }

  private boolean isEmailOrUsernameInUse(String email, String username) {
    return accountRepository.existsByEmail(email) || accountRepository.existsByUsername(username);
  }

}
