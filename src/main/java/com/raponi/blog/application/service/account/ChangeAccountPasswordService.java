package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeAccountPasswordUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public ChangeAccountPasswordService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Account handle(String accountId, String newPassword) {
    Optional<Account> existing = this.accountRepository.findById(accountId);
    if (existing.isPresent()) {
      String hashedPassword = passwordEncoder.encode(newPassword);
      User.builder().username(existing.get().username()).password(hashedPassword);
      return this.accountRepository.save(existing.get().changePassword(hashedPassword));
    }
    throw new AccountNotFound("id equals " + accountId);
  }
}
