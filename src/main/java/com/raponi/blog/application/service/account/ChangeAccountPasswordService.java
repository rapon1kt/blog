package com.raponi.blog.application.service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.application.service.JWTService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeAccountPasswordUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;
  private final AccountValidatorService accountValidatorService;

  public ChangeAccountPasswordService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
      JWTService jwtService, AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String role, String tokenId, String password, String newPassword) {

    Account acc = this.accountValidatorService.handle(tokenId, accountId, password, role);
    verifyNewPassword(newPassword, password);
    Account updatedAcc = acc.changePassword(passwordEncoder.encode(newPassword));
    this.accountRepository.save(updatedAcc);
    if (!this.accountValidatorService.isAdmin(role))
      this.jwtService.generateToken(updatedAcc.username(), updatedAcc);
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
