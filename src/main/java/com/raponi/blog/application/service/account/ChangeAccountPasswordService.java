package com.raponi.blog.application.service.account;

import com.raponi.blog.application.usecase.account.ChangeAccountPasswordCommand;
import com.raponi.blog.application.usecase.account.ChangeAccountPasswordUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.AccountNotFoundException;
import com.raponi.blog.domain.exception.InvalidParamException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountValidatorService accountValidatorService;

  public ChangeAccountPasswordService(
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, ChangeAccountPasswordCommand command) {
    Account acc = this.accountRepository.findById(accountId)
        .orElseThrow(() -> new AccountNotFoundException("This account cannot be found."));
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean correctOldPassword = passwordEncoder.matches(command.password(), acc.getPassword());
    if (!correctOldPassword)
      throw new InvalidParamException("Your password does not match the system password.");
    verifyNewPassword(command.newPassword(), command.password());
    acc.setPassword(passwordEncoder.encode(command.newPassword()));
    this.accountRepository.save(acc);
    return "Password changed with success!";
  }

  private void verifyNewPassword(String newPassword, String password) {
    if (newPassword.equals(password)) {
      throw new InvalidParamException("The new password must be different from the old one.");
    }
  }
}
