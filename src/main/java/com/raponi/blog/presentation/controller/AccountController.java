package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.ChangeAccountPasswordService;
import com.raponi.blog.application.service.account.DeleteAccountService;
import com.raponi.blog.application.service.account.FindAccountByEmailService;
import com.raponi.blog.application.service.account.FindAccountByIdService;
import com.raponi.blog.application.service.account.FindAllAccountsService;
import com.raponi.blog.application.service.account.UpdateAccountService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.helpers.HttpHelper;

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
  public ResponseEntity<?> getAllAccounts() {
    try {
      return HttpHelper.ok(this.findAllService.handle());
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAccountById(@PathVariable("id") String accountId) {
    try {
      return HttpHelper.ok(this.findAccountByIdService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping
  public ResponseEntity<?> getAccountByEmail(@RequestParam("email") String email) {
    try {
      return HttpHelper.ok(this.findAccountByEmailService.handle(email));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAccountById(@PathVariable("id") String accountId,
      @RequestBody Account newAccountInfos) {
    try {
      return HttpHelper.ok(this.updateAccountService.handle(accountId, newAccountInfos.username()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/newpassword/{id}")
  public ResponseEntity<?> changeAccountPassword(@PathVariable("id") String accountId,
      @RequestBody Account accountInfos) {
    try {
      return HttpHelper.ok(this.changeAccountPasswordService.handle(accountId, accountInfos.password()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAccountById(@PathVariable("id") String accountId) {
    try {
      return HttpHelper.ok(this.deleteAccountService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}