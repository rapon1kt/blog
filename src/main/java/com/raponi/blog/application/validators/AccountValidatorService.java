package com.raponi.blog.application.validators;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.AccountValidatorUseCase;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.InvalidParamException;

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
      Boolean passwordConfirmation = passwordMatches(password, requestAccount.getPassword());
      if (passwordConfirmation) {
        return acc.get();
      }
      throw new InvalidParamException("Your password is incorrect.");
    }
    throw new AccessDeniedException("You don't have permission to do this.");
  }

  public Account getAccountByAccountId(String accountId) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(accountId);
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("You don't have permission to do this.");
  }

  public Account getAccountByEmail(String email) {
    Optional<Account> acc = this.accountRepository.findByEmail(email);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(acc.get().getId());
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("You don't have permission to do this.");
  }

  public Account getAccountByUsername(String username) {
    Optional<Account> acc = this.accountRepository.findByUsername(username);
    Boolean verifiedAccount = verifyPresenceAndActive(acc);
    Boolean authorized = verifyAuthority(acc.get().getId());
    if (authorized && verifiedAccount) {
      return acc.get();
    }
    throw new AccessDeniedException("You don't have permission to do this.");
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
      if (!acc.get().isActive()) {
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
