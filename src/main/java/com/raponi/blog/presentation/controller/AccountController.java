package com.raponi.blog.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.DeleteAccountService;
import com.raponi.blog.application.service.account.FindAccountByIdService;
import com.raponi.blog.application.service.account.FindAllAccountsService;
import com.raponi.blog.application.service.account.UpdateAccountService;
import com.raponi.blog.domain.model.Account;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  public FindAllAccountsService findAllService;
  public FindAccountByIdService findAccountService;
  public UpdateAccountService updateAccountService;
  public DeleteAccountService deleteAccountService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService) {
    this.findAllService = findAllService;
    this.findAccountService = findAccountService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
  }

  @GetMapping("/")
  public List<Account> getAllAccounts() {
    return this.findAllService.handle();
  }

  @GetMapping("/{id}")
  public Optional<Account> getAccountById(@PathVariable("id") String accountId) {
    return this.findAccountService.handle(accountId);
  }

  @PutMapping("/{id}")
  public Account updateAccountById(@PathVariable("id") String accountId,
      @RequestBody Account newAccountInfos) {
    return this.updateAccountService.handle(accountId, newAccountInfos.username());
  }

  @DeleteMapping("/{id}")
  public String deleteAccountById(@PathVariable("id") String accountId) {
    return this.deleteAccountService.handle(accountId);
  }

}
