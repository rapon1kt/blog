package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.ban.UnbanAccountService;
import com.raponi.blog.application.service.follow.FollowAndUnfollowAccountService;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.application.service.account.*;
import com.raponi.blog.application.service.ban.BanAccountService;
import com.raponi.blog.presentation.dto.request.BanAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.ChangePasswordRequestDTO;
import com.raponi.blog.presentation.dto.request.DeleteAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.UpdateAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.AccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;
import com.raponi.blog.presentation.mapper.BanMapper;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final BanMapper banMapper;
  private final AccountMapper mapper;
  private final BanAccountService banAccountService;
  private final UnbanAccountService unbanAccountService;
  private final UpdateAccountService updateAccountService;
  private final DeleteAccountService deleteAccountService;
  private final FindAccountByIdService findAccountByIdService;
  private final FindAccountPostsService findAccountPostsService;
  private final BlockAndUnblockAccountService blockAccountService;
  private final FindAccountFollowersService findAccountFollowersService;
  private final FindAccountFollowingService findAccountFollowingService;
  private final ChangeAccountPasswordService changeAccountPasswordService;
  private final FindAccountByUsernameService findAccountByUsernameService;
  private final FollowAndUnfollowAccountService followAndUnfollowAccountService;

  public AccountController(
      BanMapper banMapper,
      AccountMapper mapper,
      BanAccountService banAccountService,
      UnbanAccountService unbanAccountService,
      UpdateAccountService updateAccountService,
      DeleteAccountService deleteAccountService,
      FindAccountByIdService findAccountByIdService,
      FindAccountPostsService findAccountPostsService,
      BlockAndUnblockAccountService blockAccountService,
      FindAccountFollowersService findAccountFollowersService,
      FindAccountFollowingService findAccountFollowingService,
      ChangeAccountPasswordService changeAccountPasswordService,
      FindAccountByUsernameService findAccountByUsernameService,
      FollowAndUnfollowAccountService followAndUnfollowAccountService) {
    this.mapper = mapper;
    this.banMapper = banMapper;
    this.banAccountService = banAccountService;
    this.unbanAccountService = unbanAccountService;
    this.blockAccountService = blockAccountService;
    this.updateAccountService = updateAccountService;
    this.deleteAccountService = deleteAccountService;
    this.findAccountByIdService = findAccountByIdService;
    this.findAccountPostsService = findAccountPostsService;
    this.findAccountFollowersService = findAccountFollowersService;
    this.findAccountFollowingService = findAccountFollowingService;
    this.changeAccountPasswordService = changeAccountPasswordService;
    this.findAccountByUsernameService = findAccountByUsernameService;
    this.followAndUnfollowAccountService = followAndUnfollowAccountService;
  }

  @GetMapping("/me")
  public ResponseEntity<AccountResponseDTO> getMyAccount(Authentication authentication) {
    String accountId = authentication.getName();
    var account = this.findAccountByIdService.handle(accountId);
    var response = mapper.toResponse(account);
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/me", consumes = "multipart/form-data")
  public ResponseEntity<AccountResponseDTO> updateMyAccount(
      Authentication authentication,
      @RequestPart(required = false, value = "requestDTO") @Valid UpdateAccountRequestDTO requestDTO,
      @RequestPart(required = false, value = "image") MultipartFile image) throws IOException {
    String accountId = authentication.getName();
    var command = mapper.toUpdateCommand(requestDTO);
    var account = this.updateAccountService.handle(accountId, command, image);
    var response = mapper.toResponse(account);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/me/new-password")
  public ResponseEntity<String> changeMyPassword(
      Authentication authentication,
      @RequestBody @Valid ChangePasswordRequestDTO requestDTO) {
    String accountId = authentication.getName();
    var command = mapper.toPasswordCommand(requestDTO);
    var response = this.changeAccountPasswordService.handle(accountId, command);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/me")
  public ResponseEntity<String> deleteMyAccount(
      Authentication authentication,
      @RequestBody @Valid DeleteAccountRequestDTO requestDTO) {
    String accountId = authentication.getName();
    return ResponseEntity.ok(this.deleteAccountService.handle(accountId, requestDTO.getPassword()));
  }

  @GetMapping("/{username}")
  public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountByUsernameService.handle(username));
  }

  @GetMapping("/{username}/posts")
  public ResponseEntity<?> getAccountPosts(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountPostsService.handle(username));
  }

  @GetMapping("/{username}/followers")
  public ResponseEntity<?> getAccountFollowers(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountFollowersService.handle(username));
  }

  @GetMapping("/{username}/following")
  public ResponseEntity<?> getAccountFollowing(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountFollowingService.handle(username));
  }

  @PostMapping("/{username}/follow")
  public ResponseEntity<?> followAndUnfollow(@PathVariable("username") String username, Authentication auth) {
    return ResponseEntity.ok(this.followAndUnfollowAccountService.handle(auth.getName(), username));
  }

  @PostMapping("/{username}/block")
  public ResponseEntity<?> blockAccount(@PathVariable("username") String username, Authentication auth) {
    return ResponseEntity.ok(this.blockAccountService.handle(auth.getName(), username));
  }

  @PostMapping("/{username}")
  public ResponseEntity<Ban> banAccount(
      @PathVariable("username") String username,
      @RequestParam("reason") BanReason reason,
      Authentication auth,
      @RequestBody @Valid BanAccountRequestDTO requestDTO) {
    var command = this.banMapper.toCommand(requestDTO);
    var response = this.banAccountService.handle(auth.getName(), username, reason, command);
    return ResponseEntity.status(201).body(response);
  }

  @PatchMapping("/{username}")
  public ResponseEntity<?> unBanAccount(@PathVariable("username") String username, Authentication auth) {
    return ResponseEntity.ok(this.unbanAccountService.handle(auth.getName(), username));
  }
}
