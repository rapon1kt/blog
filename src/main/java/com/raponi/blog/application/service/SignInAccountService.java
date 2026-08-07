package com.raponi.blog.application.service;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.command.SignInAccountCommand;
import com.raponi.blog.application.result.SignInAccountResult;
import com.raponi.blog.application.usecase.SignInAccountUseCase;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.AccountRepository;
import com.raponi.blog.domain.port.PasswordEncoderPort;
import com.raponi.blog.domain.port.TokenGeneratorPort;

@Service
public class SignInAccountService implements SignInAccountUseCase {

  public AccountRepository accountRepository;
  public TokenGeneratorPort tokenGeneratorPort;
  public PasswordEncoderPort passwordEncoderPort;

  public SignInAccountService(AccountRepository accountRepository, TokenGeneratorPort tokenGeneratorPort,
      PasswordEncoderPort passwordEncoderPort) {
    this.accountRepository = accountRepository;
    this.tokenGeneratorPort = tokenGeneratorPort;
    this.passwordEncoderPort = passwordEncoderPort;
  }

  @Override
  public SignInAccountResult handle(SignInAccountCommand command) {
    Account validAccount = validateAccount(command.email(), command.password());
    String token = tokenGeneratorPort.generateToken(validAccount);
    return new SignInAccountResult(validAccount, token);
  }

  private Account validateAccount(String email, String password) {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials."));
    boolean isPasswordValid = passwordEncoderPort.matches(password, account.getPassword());
    if (!isPasswordValid) {
      throw new InvalidCredentialsException("Invalid credentials.");
    }
    return account;
  }

}
