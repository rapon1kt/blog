package com.raponi.blog.application.service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeAccountPasswordUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountValidatorService accountValidatorService;

  public ChangeAccountPasswordService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String password, String newPassword) {
    Account acc = this.accountValidatorService.getAccountWithPasswordConfirmation(accountId, password);
    verifyNewPassword(newPassword, password);
    Account updatedAcc = acc.changePassword(passwordEncoder.encode(newPassword));
    this.accountRepository.save(updatedAcc);
    return "A senha foi alterada com sucesso!";
  }

  private void verifyNewPassword(String newPassword, String password) {
    if (newPassword.isBlank() || newPassword.length() < 8) {
      throw new IllegalArgumentException("Nova senha deve ter pelo menos 8 caracteres.");
    } else if (newPassword.equals(password)) {
      throw new IllegalArgumentException("A nova senha deve ser distinta da antiga.");
    }
  }
}
