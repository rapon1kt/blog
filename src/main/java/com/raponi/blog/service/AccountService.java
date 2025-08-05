package com.raponi.blog.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.repository.AccountRepository;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;
  private PasswordEncoder passwordEncoder;

  public AccountService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public Account createNewAccount(Account newAccount) {
    newAccount.setCreatedAt(Instant.now());
    newAccount.setModifiedAt(Instant.now());
    newAccount.setPassword(this.passwordEncoder.encode(newAccount.getPassword()));
    return this.accountRepository.save(newAccount);
  }

  public List<Account> getAllAccounts() {
    return this.accountRepository.findAll();
  }

  public Optional<Account> getAccountById(String accountId) {
    return this.accountRepository.findById(accountId);
  }

  public Account updateAccountById(String accountId, Account newAcountInfos) {
    Optional<Account> accountToUpdate = this.getAccountById(accountId);
    if (accountToUpdate.isPresent()) {
      Account account = accountToUpdate.get();
      newAcountInfos.setId(account.getId());
      newAcountInfos.setCreatedAt(account.getCreatedAt());
      newAcountInfos.setModifiedAt(Instant.now());
    }
    return this.accountRepository.save(newAcountInfos);
  }

  public String deleteAccountById(String accountId) {
    this.accountRepository.deleteById(accountId);
    return "Conta deletada com sucesso!";
  }

}
