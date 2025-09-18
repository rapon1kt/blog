package com.raponi.blog.presentation.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private BlockAndUnblockAccountService blockAccountService;
  private FindAllAccountsService findAllService;
  private FindAccountByIdService findAccountByIdService;
  private FindAccountByEmailService findAccountByEmailService;
  private UpdateAccountInfosService updateAccountInfosService;
  private DeleteAccountService deleteAccountService;
  private ChangeAccountPasswordService changeAccountPasswordService;
  private UpdateAccountStatusService updateAccountStatusService;
  private FindAccountLikesService findAccountLikesService;

  public AccountController(FindAllAccountsService findAllService, FindAccountByIdService findAccountByIdService,
      FindAccountByEmailService findAccountByEmailService,
      UpdateAccountInfosService updateAccountInfosService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService,
      FindAccountLikesService findAccountLikesService, BlockAndUnblockAccountService blockAccountService) {
    this.findAllService = findAllService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountByEmailService = findAccountByEmailService;
    this.updateAccountInfosService = updateAccountInfosService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.updateAccountStatusService = updateAccountStatusService;
    this.findAccountLikesService = findAccountLikesService;
    this.blockAccountService = blockAccountService;
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

  @PutMapping("/{accountId}/newpassword")
  public ResponseEntity<?> changeAccountPassword(@PathVariable("accountId") String accountId,
      @RequestBody @Valid UpdateAccountPasswordRequestDTO requestDTO) {
    return ResponseEntity.ok(this.changeAccountPasswordService.handle(accountId, requestDTO));
  }

  @PutMapping("/{accountId}/status")
  public ResponseEntity<?> updateStatus(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.updateAccountStatusService.handle(accountId));
  }

  @GetMapping("/{accountId}/likes")
  public ResponseEntity<?> getAccountLikes(@PathVariable("accountId") String accountId) {
    return ResponseEntity.ok(this.findAccountLikesService.handle(accountId));
  }

  @PostMapping("/{blockedId}/block")
  public ResponseEntity<?> blockAccount(@PathVariable("blockedId") String blockedId, Authentication auth) {
    return ResponseEntity.status(201).body(this.blockAccountService.handle(auth.getName(), blockedId));
  }

}