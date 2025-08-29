package com.raponi.blog.application.service;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.AccountValidatorUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class AccountValidatorService implements AccountValidatorUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountValidatorService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private Authentication getAuth() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public Account getAccountWithPasswordConfirmation(String accountId, String password) {

    String tokenId = this.getAuth().getName();

    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(accountId);
    Account requestAccount = this.accountRepository.findById(tokenId).get();
    if (authorized && verifiedAccount) {
      Boolean passwordConfirmation = passwordMatches(password, requestAccount.password());
      if (passwordConfirmation) {
        return acc.get();
      }
      throw new IllegalArgumentException("A sua senha está incorreta!");
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByAccountId(String accountId) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(accountId);
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByEmail(String email) {
    Optional<Account> acc = this.accountRepository.findByEmail(email);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(acc.get().id());
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByUsername(String username) {
    Optional<Account> acc = this.accountRepository.findByUsername(username);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(acc.get().id());
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public boolean isAdmin() {
    String role = this.getAuth().getAuthorities().iterator().next().getAuthority();
    return role.equals("ROLE_ADMIN");
  }

  public boolean verifyAuthority(String accountId) {
    if (!accountId.equals(this.getAuth().getName())) {
      if (!isAdmin()) {
        return false;
      }
      return true;
    }
    return true;
  }

  public boolean verifyPresenceAndActive(Optional<Account> acc) {
    if (acc.isPresent()) {
      if (isAdmin()) {
        return true;
      }
      if (!acc.get().active()) {
        return false;
      }
      return true;
    }
    return false;
  }

  private boolean passwordMatches(String password, String hashedPassword) {
    return this.passwordEncoder.matches(password, hashedPassword);
  }

}
