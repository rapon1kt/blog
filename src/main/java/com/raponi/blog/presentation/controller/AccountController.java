package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.*;
import com.raponi.blog.presentation.helpers.HttpHelper;

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
  private FindAccountPostsService findAccountPostsService;
  private FindAccountLikesService findAccountLikesService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      FindAccountByUsernameService findAccountByUsernameService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService, FindAccountPostsService findAccountPostsService,
      FindAccountLikesService findAccountLikesService) {
    this.findAllService = findAllService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountByEmailService = findAccountByEmailService;
    this.findAccountByUsernameService = findAccountByUsernameService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.updateAccountStatusService = updateAccountStatusService;
    this.findAccountPostsService = findAccountPostsService;
    this.findAccountLikesService = findAccountLikesService;
  }

  @GetMapping
  public ResponseEntity<?> getAllAccounts() {
    try {
      return HttpHelper.ok(this.findAllService.handle());
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<?> getAccountById(@PathVariable("accountId") String accountId) {
    try {
      return HttpHelper.ok(this.findAccountByIdService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/{accountId}")
  public ResponseEntity<?> updateAccountById(@PathVariable("accountId") String accountId,
      @RequestBody String newUsername) {
    try {
      return HttpHelper.ok(this.updateAccountService.handle(accountId, newUsername));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId) {
    try {
      return HttpHelper.ok(this.deleteAccountService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> getAccountByEmail(@PathVariable("email") String email) {
    try {
      return HttpHelper.ok(this.findAccountByEmailService.handle(email));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
    try {
      return HttpHelper.ok(this.findAccountByUsernameService.handle(username));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/{accountId}/newpassword")
  public ResponseEntity<?> changeAccountPassword(@PathVariable("accountId") String accountId,
      @RequestBody String newPassword) {
    try {
      return HttpHelper.ok(this.changeAccountPasswordService.handle(accountId, newPassword));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/{accountId}/status")
  public ResponseEntity<?> updateStatus(@PathVariable("accountId") String accountId) {
    try {
      return HttpHelper.ok(this.updateAccountStatusService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{accountId}/posts")
  public ResponseEntity<?> getAccountPosts(@PathVariable("accountId") String accountId, Authentication auth) {
    try {
      return HttpHelper.ok(this.findAccountPostsService.handle(accountId, auth.getName()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{accountId}/likes")
  public ResponseEntity<?> getAccountLikes(@PathVariable("accountId") String accountId, Authentication auth) {
    try {
      return HttpHelper.ok(this.findAccountLikesService.handle(accountId, auth.getName()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}