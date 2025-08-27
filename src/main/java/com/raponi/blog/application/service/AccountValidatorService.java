package com.raponi.blog.application.service;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
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

  public Account getAccountWithPasswordConfirmation(String tokenId, String accountId, String password, String role) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean authorized = verifyAuthority(accountId, tokenId, role);
    Account requestAccount = this.accountRepository.findById(tokenId).get();
    if (authorized) {
      Account verifiedAccount = verifyPresenceAndActive(acc, role);
      Boolean passwordConfirmation = passwordMatches(password, requestAccount.password());
      if (passwordConfirmation) {
        return verifiedAccount;
      }
      throw new IllegalArgumentException("A sua senha está incorreta!");
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByAccountId(String accountId, String tokenId, String role) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean authorized = verifyAuthority(accountId, tokenId, role);
    if (authorized) {
      Account verifiedAccount = verifyPresenceAndActive(acc, role);
      return verifiedAccount;
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByEmail(String tokenId, String role, String email) {
    Optional<Account> acc = this.accountRepository.findByEmail(email);
    Boolean authorized = verifyAuthority(acc.get().id(), tokenId, role);
    if (authorized) {
      Account verifiedAccount = verifyPresenceAndActive(acc, role);
      return verifiedAccount;
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  public Account getAccountByUsername(String tokenId, String role, String username) {
    Optional<Account> acc = this.accountRepository.findByUsername(username);
    Boolean authorized = verifyAuthority(acc.get().id(), tokenId, role);
    if (authorized) {
      Account verifiedAccount = verifyPresenceAndActive(acc, role);
      return verifiedAccount;
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

  private boolean isAdmin(String role) {
    return role.equals("ROLE_ADMIN");
  }

  private boolean verifyAuthority(String accountId, String tokenId, String role) {
    if (!accountId.equals(tokenId)) {
      if (!isAdmin(role)) {
        return false;
      }
      return true;
    }
    return true;
  }

  public Account verifyPresenceAndActive(Optional<Account> acc, String role) {
    if (acc.isPresent()) {
      if (isAdmin(role)) {
        return acc.get();
      }

      if (!acc.get().active()) {
        throw new AccessDeniedException("Você não tem permissão para fazer isso.");
      }

      return acc.get();
    }
    throw new IllegalArgumentException("O usuário em questão não existe.");
  }

  private boolean passwordMatches(String password, String hashedPassword) {
    return this.passwordEncoder.matches(password, hashedPassword);
  }

}
