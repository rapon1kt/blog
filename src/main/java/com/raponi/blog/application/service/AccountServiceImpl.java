
package com.raponi.blog.application.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.usecase.AccountService;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoderService;

  public AccountServiceImpl(PasswordEncoderService passwordEncoderService, AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    this.passwordEncoderService = passwordEncoderService;
  }

  @Override
  public Account createNewAccount(Account newAccount) {
    String hashedPassword = passwordEncoderService.encode(newAccount.password());
    Account accountToSave = new Account(
        newAccount.id(),
        newAccount.email(),
        newAccount.username(),
        hashedPassword,
        Instant.now(),
        Instant.now());
    return this.accountRepository.save(accountToSave);
  }

  @Override
  public List<Account> getAllAccounts() {
    return this.accountRepository.findAll();
  }

  @Override
  public Optional<Account> getAccountById(String accountId) {
    return this.accountRepository.findById(accountId);
  }

  @Override
  public Account updateAccountById(String accountId, Account newAcountInfos) {
    Optional<Account> accountToUpdate = this.getAccountById(accountId);
    if (accountToUpdate.isPresent()) {
      Account updated = new Account(
          accountToUpdate.get().id(),
          newAcountInfos.email(),
          newAcountInfos.username(),
          newAcountInfos.password(),
          accountToUpdate.get().createdAt(),
          Instant.now());
      return this.accountRepository.save(updated);
    }
    return null;
  }

  public String deleteAccountById(String accountId) {
    this.accountRepository.deleteById(accountId);
    return "Conta deletada com sucesso!";
  }

}
