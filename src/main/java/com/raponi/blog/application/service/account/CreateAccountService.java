package com.raponi.blog.application.service.account;

import com.raponi.blog.application.usecase.account.CreateAccountCommand;
import com.raponi.blog.application.usecase.account.CreateAccountUseCase;
import com.raponi.blog.domain.exception.EmailAlreadyRegisteredException;
import com.raponi.blog.domain.exception.UsernameAlreadyRegisteredException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoderService;

  public CreateAccountService(AccountRepository accountRepository, PasswordEncoderService passwordEncoderService) {
    this.accountRepository = accountRepository;
    this.passwordEncoderService = passwordEncoderService;
  }

  @Override
  public Account handle(CreateAccountCommand command) {
    this.validateAvailability(command.username(), command.email());
    String hashedPassword = this.passwordEncoderService.encode(command.password());
    Account account = Account.create(command.email(), command.username(), hashedPassword);
    Account savedAccount = this.accountRepository.save(account);
    return savedAccount;
  }

  private void validateAvailability(String username, String email) {
    if (this.accountRepository.existsByEmail(email)) {
      throw new EmailAlreadyRegisteredException("E-mail already registered.");
    }
    if (this.accountRepository.existsByUsername(username)) {
      throw new UsernameAlreadyRegisteredException("Username already registered.");
    }
  }
}
