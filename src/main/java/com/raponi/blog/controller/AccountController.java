package com.raponi.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  public AccountService accountService;

  @GetMapping("/")
  public List<Account> getAllAccounts() {
    return this.accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public Optional<Account> getAccountById(@PathVariable("id") String accountId) {
    return this.accountService.getAccountById(accountId);
  }

  @PutMapping("/{id}")
  public Account updateAccountById(@PathVariable("id") String accountId, @RequestBody Account newAccountInfos) {
    return this.accountService.updateAccountById(accountId, newAccountInfos);
  }

  @DeleteMapping("/{id}")
  public String deleteAccountById(@PathVariable("id") String accountId) {
    return this.accountService.deleteAccountById(accountId);
  }

}
