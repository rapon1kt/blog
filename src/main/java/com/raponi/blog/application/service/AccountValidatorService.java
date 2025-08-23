package com.raponi.blog.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.AccountValidatorUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class AccountValidatorService implements AccountValidatorUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountValidatorService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Account handle(String tokenId, String accountId, String password, String role) {
    Account account = this.accountRepository.findById(accountId)
        .orElseThrow(() -> new AccountNotFound("id equals " + accountId));

    if (!accountId.equals(tokenId) && !isAdmin(role)) {
      throw new IllegalArgumentException("Você não tem permissão para fazer isso.");
    } else if (!account.active()) {
      throw new IllegalArgumentException("Essa conta está desativada");
    } else if (!passwordMatches(password, account.password())) {
      throw new IllegalArgumentException("Sua senha está incorreta.");
    }
    return account;
  }

  public boolean isAdmin(String role) {
    return role.equals("ROLE_ADMIN");
  }

  public boolean passwordMatches(String password, String hashedPassword) {
    return this.passwordEncoder.matches(password, hashedPassword);
  }

}
