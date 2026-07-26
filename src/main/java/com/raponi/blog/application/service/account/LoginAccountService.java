package com.raponi.blog.application.service.account;

import com.raponi.blog.application.usecase.account.LoginAccountCommand;
import com.raponi.blog.application.usecase.account.LoginAccountUseCase;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.port.TokenGeneratorService;
import com.raponi.blog.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountService implements LoginAccountUseCase {

  private final AccountRepository accountRepository;
  private final TokenGeneratorService tokenGenerator;
  private final PasswordEncoderService passwordEncoder;

  public LoginAccountService(AccountRepository accountRepository, TokenGeneratorService tokenGenerator, PasswordEncoderService passwordEncoder) {
    this.tokenGenerator = tokenGenerator;
    this.passwordEncoder = passwordEncoder;
    this.accountRepository = accountRepository;
  }

  @Override
  public String handle(LoginAccountCommand command) {
    Account account = this.accountRepository
      .findByUsername(command.username())
      .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

    if (!this.passwordEncoder.matches(command.password(), account.getPassword())) {
      throw new InvalidCredentialsException("Invalid username or password");
    }

    return this.tokenGenerator.generateToken(account);
  }
}
