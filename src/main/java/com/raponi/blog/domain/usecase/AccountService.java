package com.raponi.blog.domain.usecase;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Account;

public interface AccountService {

  public Account createNewAccount(Account newAccount);

  public List<Account> getAllAccounts();

  public Optional<Account> getAccountById(String accountId);

  public Account updateAccountById(String accountId, Account newAcountInfos);

  public String deleteAccountById(String accountId);

}
