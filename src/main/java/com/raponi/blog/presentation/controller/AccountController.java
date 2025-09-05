package com.raponi.blog.presentation.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.application.service.account.*;
import com.raponi.blog.presentation.dto.UpdateAccountPasswordRequestDTO;
import com.raponi.blog.presentation.dto.DeleteAccountRequestDTO;
import com.raponi.blog.presentation.dto.UpdateAccountInfosRequestDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private FindAllAccountsService findAllService;
  private FindAccountByIdService findAccountByIdService;
  private FindAccountByEmailService findAccountByEmailService;
  private FindAccountByUsernameService findAccountByUsernameService;
  private UpdateAccountInfosService updateAccountInfosService;
  private DeleteAccountService deleteAccountService;
  private ChangeAccountPasswordService changeAccountPasswordService;
  private UpdateAccountStatusService updateAccountStatusService;
  private FindAccountPostsService findAccountPostsService;
  private FindAccountLikesService findAccountLikesService;
  private FindAccountFollowersService findAccountFollowersService;
  private final FindAccountFollowingService findAccountFollowingService;
  private final FindAccountPicturesService findAccountPicturesService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      FindAccountByUsernameService findAccountByUsernameService,
      UpdateAccountInfosService updateAccountInfosService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService, FindAccountPostsService findAccountPostsService,
      FindAccountLikesService findAccountLikesService, FindAccountFollowersService findAccountFollowersService,
      FindAccountFollowingService findAccountFollowingService, FindAccountPicturesService findAccountPicturesService) {
    this.findAllService = findAllService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountByEmailService = findAccountByEmailService;
    this.findAccountByUsernameService = findAccountByUsernameService;
    this.updateAccountInfosService = updateAccountInfosService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.updateAccountStatusService = updateAccountStatusService;
    this.findAccountPostsService = findAccountPostsService;
    this.findAccountLikesService = findAccountLikesService;
    this.findAccountFollowersService = findAccountFollowersService;
    this.findAccountFollowingService = findAccountFollowingService;
    this.findAccountPicturesService = findAccountPicturesService;
  }

  @GetMapping
  public ResponseEntity<?> getAllAccounts() {
    return ResponseEntity.ok(this.findAllService.handle());
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<?> getAccountById(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountByIdService.handle(accountId));
  }

  @PutMapping(path = "/{accountId}", consumes = "multipart/form-data")
  public ResponseEntity<?> updateAccountById(@PathVariable("accountId") String accountId,
      @RequestPart(required = false, value = "requestDTO") @Valid UpdateAccountInfosRequestDTO requestDTO,
      @RequestPart(required = false, value = "image") MultipartFile image) throws IOException {
    return ResponseEntity.ok(this.updateAccountInfosService.handle(accountId, requestDTO, image));
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId,
      @RequestBody @Valid DeleteAccountRequestDTO requestDTO) {
    return ResponseEntity.ok(this.deleteAccountService.handle(accountId, requestDTO));
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> getAccountByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok(this.findAccountByEmailService.handle(email));
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountByUsernameService.handle(username));
  }

  @PutMapping("/{accountId}/newpassword")
  public ResponseEntity<?> changeAccountPassword(@PathVariable("accountId") String accountId,
      @RequestBody @Valid UpdateAccountPasswordRequestDTO requestDTO) {
    return ResponseEntity.ok(this.changeAccountPasswordService.handle(accountId, requestDTO));
  }

  @PutMapping("/{accountId}/status")
  public ResponseEntity<?> updateStatus(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.updateAccountStatusService.handle(accountId));
  }

  @GetMapping("/{accountId}/posts")
  public ResponseEntity<?> getAccountPosts(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountPostsService.handle(accountId));
  }

  @GetMapping("/{accountId}/likes")
  public ResponseEntity<?> getAccountLikes(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountLikesService.handle(accountId));
  }

  @GetMapping("/{accountId}/followers")
  public ResponseEntity<?> getAccountFollowers(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountFollowersService.handle(accountId));
  }

  @GetMapping("/{accountId}/following")
  public ResponseEntity<?> getAccountFollowing(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountFollowingService.handle(accountId));
  }

  @GetMapping("/{accountId}/picture")
  public ResponseEntity<?> getAccountPicture(@PathVariable("accountId") String accountId) throws IOException {
    return ResponseEntity.ok(this.findAccountPicturesService.handle(accountId));
  }
}