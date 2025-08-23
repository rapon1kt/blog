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
  private FindAccountPostsService findAccountPostsService;
  private FindAccountLikesService findAccountLikesService;
  private FindAccountFollowersService findAccountFollowersService;
  private final FindAccountFollowingService findAccountFollowingService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      FindAccountByUsernameService findAccountByUsernameService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService, FindAccountPostsService findAccountPostsService,
      FindAccountLikesService findAccountLikesService, FindAccountFollowersService findAccountFollowersService,
      FindAccountFollowingService findAccountFollowingService) {
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
    this.findAccountFollowersService = findAccountFollowersService;
    this.findAccountFollowingService = findAccountFollowingService;
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
      @RequestBody Http.UpdateBody body) {
    try {
      return HttpHelper.ok(this.updateAccountService.handle(accountId, body.username()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId,
      @RequestBody Http.AuthBody body, Authentication auth) {
    try {
      String role = auth.getAuthorities().iterator().next().getAuthority();
      String tokenId = auth.getName();
      return HttpHelper.ok(this.deleteAccountService.handle(tokenId, accountId, role, body.password()));
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
      @RequestBody Http.UpdateBody body, Authentication auth) {
    try {
      String role = auth.getAuthorities().iterator().next().getAuthority();
      String tokenId = auth.getName();
      return HttpHelper
          .ok(this.changeAccountPasswordService.handle(accountId, role, tokenId, body.password(), body.newPassword()));
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

  @GetMapping("/{accountId}/followers")
  public ResponseEntity<?> getAccountFollowers(@PathVariable("accountId") String accountId) {
    try {
      return HttpHelper.ok(this.findAccountFollowersService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{accountId}/following")
  public ResponseEntity<?> getAccountFollowing(@PathVariable("accountId") String accountId) {
    try {
      return HttpHelper.ok(this.findAccountFollowingService.handle(accountId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}