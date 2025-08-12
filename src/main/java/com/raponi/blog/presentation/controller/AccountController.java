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

import com.raponi.blog.application.service.account.ChangeAccountPasswordService;
import com.raponi.blog.application.service.account.DeleteAccountService;
import com.raponi.blog.application.service.account.FindAccountByEmailService;
import com.raponi.blog.application.service.account.FindAccountByIdService;
import com.raponi.blog.application.service.account.FindAllAccountsService;
import com.raponi.blog.application.service.account.UpdateAccountService;
import com.raponi.blog.domain.model.Account;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  public FindAllAccountsService findAllService;
  public FindAccountByIdService findAccountByIdService;
  public FindAccountByEmailService findAccountByEmailService;
  public UpdateAccountService updateAccountService;
  public DeleteAccountService deleteAccountService;
  public ChangeAccountPasswordService changeAccountPasswordService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService) {
    this.findAllService = findAllService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountByEmailService = findAccountByEmailService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
  }

  @GetMapping("/")
  public List<Account> getAllAccounts() {
    return this.findAllService.handle();
  }

  @GetMapping("/{id}")
  public Optional<Account> getAccountById(@PathVariable("id") String accountId) {
    return this.findAccountByIdService.handle(accountId);
  }

  @GetMapping("/{email}")
  public Optional<Account> getAccountByEmai(@PathVariable("email") String email) {
    return this.findAccountByEmailService.handle(email);
  }

  @PutMapping("/{id}")
  public Account updateAccountById(@PathVariable("id") String accountId,
      @RequestBody Account newAccountInfos) {
    return this.updateAccountService.handle(accountId, newAccountInfos.username());
  }

  @PutMapping("/newpassword/{id}")
  public Account changeAccountPassword(@PathVariable("id") String accountId, @RequestBody Account accountInfos) {
    return this.changeAccountPasswordService.handle(accountId, accountInfos.password());
  }

  @DeleteMapping("/{id}")
  public String deleteAccountById(@PathVariable("id") String accountId) {
    return this.deleteAccountService.handle(accountId);
  }

}
