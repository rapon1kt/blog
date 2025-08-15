package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.ChangeAccountPasswordService;
import com.raponi.blog.application.service.account.DeleteAccountService;
import com.raponi.blog.application.service.account.FindAccountByEmailService;
import com.raponi.blog.application.service.account.FindAccountByIdService;
import com.raponi.blog.application.service.account.FindAccountByUsernameService;
import com.raponi.blog.application.service.account.FindAllAccountsService;
import com.raponi.blog.application.service.account.UpdateAccountService;
import com.raponi.blog.application.service.account.UpdateAccountStatusService;
import com.raponi.blog.presentation.helpers.HttpHelper;
import com.raponi.blog.presentation.protocols.Http;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private FindAllAccountsService findAllService;
  private FindAccountByIdService findAccountByIdService;
  private FindAccountByEmailService findAccountByEmailService;
  private FindAccountByUsernameService findAccountByUsernameService;
  private UpdateAccountService updateAccountService;
  private DeleteAccountService deleteAccountService;
  private ChangeAccountPasswordService changeAccountPasswordService;
  private UpdateAccountStatusService updateAccountStatusService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      FindAccountByUsernameService findAccountByUsernameService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService) {
    this.findAllService = findAllService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountByEmailService = findAccountByEmailService;
    this.findAccountByUsernameService = findAccountByUsernameService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.updateAccountStatusService = updateAccountStatusService;
  }

  @GetMapping
  public ResponseEntity<?> getAllAccounts() {
    try {
      return HttpHelper.ok(this.findAllService.handle());
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/id")
  public ResponseEntity<?> getAccountById(@RequestParam("target") String accountId) {
    try {
      return HttpHelper.ok(this.findAccountByIdService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/id")
  public ResponseEntity<?> updateAccountById(@RequestParam("target") String accountId,
      @RequestBody Http.UpdateBody updateBody) {
    try {
      return HttpHelper.ok(this.updateAccountService.handle(accountId, updateBody.username()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/id")
  public ResponseEntity<?> deleteAccountById(@RequestParam("target") String accountId) {
    try {
      return HttpHelper.ok(this.deleteAccountService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/email")
  public ResponseEntity<?> getAccountByEmail(@RequestParam("target") String email) {
    try {
      return HttpHelper.ok(this.findAccountByEmailService.handle(email));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/username")
  public ResponseEntity<?> getAccountByUsername(@RequestParam("target") String username) {
    try {
      return HttpHelper.ok(this.findAccountByUsernameService.handle(username));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/newpassword")
  public ResponseEntity<?> changeAccountPassword(@RequestParam("target") String accountId,
      @RequestBody Http.UpdateBody updateBody) {
    try {
      return HttpHelper.ok(this.changeAccountPasswordService.handle(accountId, updateBody.password()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/status")
  public ResponseEntity<?> updateStatus(@RequestParam("target") String accountId,
      @RequestBody Http.UpdateBody updateBody) {
    try {
      return HttpHelper.ok(this.updateAccountStatusService.handle(accountId, updateBody.active()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}