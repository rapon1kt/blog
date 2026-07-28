package com.raponi.blog.presentation.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.application.service.account.*;
import com.raponi.blog.presentation.dto.request.ChangePasswordRequestDTO;
import com.raponi.blog.presentation.dto.request.DeleteAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.UpdateAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.AccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private BlockAndUnblockAccountService blockAccountService;
  private FindAccountByIdService findAccountByIdService;
  private UpdateAccountService updateAccountService;
  private DeleteAccountService deleteAccountService;
  private ChangeAccountPasswordService changeAccountPasswordService;
  private UpdateAccountStatusService updateAccountStatusService;
  private FindAccountLikesService findAccountLikesService;
  private AccountMapper mapper;

  public AccountController(FindAccountByIdService findAccountByIdService,
      UpdateAccountService updateAccountService, DeleteAccountService deleteAccountService,
      ChangeAccountPasswordService changeAccountPasswordService,
      UpdateAccountStatusService updateAccountStatusService,
      FindAccountLikesService findAccountLikesService, BlockAndUnblockAccountService blockAccountService,
      AccountMapper mapper) {
    this.findAccountByIdService = findAccountByIdService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.updateAccountStatusService = updateAccountStatusService;
    this.findAccountLikesService = findAccountLikesService;
    this.blockAccountService = blockAccountService;
    this.mapper = mapper;
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("accountId") String accountId) {
    var account = this.findAccountByIdService.handle(accountId);
    var response = mapper.toResponse(account);
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/{accountId}", consumes = "multipart/form-data")
  public ResponseEntity<?> updateAccountById(@PathVariable("accountId") String accountId,
      @RequestPart(required = false, value = "requestDTO") @Valid UpdateAccountRequestDTO requestDTO,
      @RequestPart(required = false, value = "image") MultipartFile image) throws IOException {
        var command = mapper.toUpdateCommand(requestDTO);
        var response = this.updateAccountService.handle(accountId, command, image);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{accountId}")
  public ResponseEntity<?> deleteAccountById(@PathVariable("accountId") String accountId,
      @RequestBody @Valid DeleteAccountRequestDTO requestDTO) {
    return ResponseEntity.ok(this.deleteAccountService.handle(accountId, requestDTO.getPassword()));
  }

  @PatchMapping("/{accountId}/newpassword")
  public ResponseEntity<String> changeAccountPassword(@PathVariable("accountId") String accountId,
      @RequestBody @Valid ChangePasswordRequestDTO requestDTO) {
    var command = mapper.toPasswordCommand(requestDTO);
    var response = this.changeAccountPasswordService.handle(accountId, command);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{accountId}/status")
  public ResponseEntity<AccountResponseDTO> updateStatus(@PathVariable("accountId") String accountId) {
    var account = this.updateAccountStatusService.handle(accountId);
    var response = mapper.toResponse(account);
    return ResponseEntity.ok(response);
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
