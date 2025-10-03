package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.ban.BanAccountService;
import com.raponi.blog.application.service.ban.FindAccountBansService;
import com.raponi.blog.application.service.ban.UnbanAccountService;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.presentation.dto.BanAccountRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/bans")
@PreAuthorize("hasRole('ADMIN')")
public class BanController {

  private final BanAccountService banAccountService;
  private final FindAccountBansService findAccountBansService;
  private final UnbanAccountService unbanAccountService;

  public BanController(BanAccountService banAccountService, FindAccountBansService findAccountBansService,
      UnbanAccountService unbanAccountService) {
    this.banAccountService = banAccountService;
    this.findAccountBansService = findAccountBansService;
    this.unbanAccountService = unbanAccountService;
  }

  @PostMapping("/{bannedId}")
  public ResponseEntity<?> banAccount(@PathVariable("bannedId") String bannedId,
      @RequestParam("reason") BanReason reason, Authentication auth,
      @RequestBody @Valid BanAccountRequestDTO requestDTO) {
    return ResponseEntity.ok(this.banAccountService.handle(auth.getName(), bannedId, reason, requestDTO));
  }

  @PatchMapping("/{bannedId}")
  public ResponseEntity<?> unBanAccount(@PathVariable("bannedId") String bannedId, Authentication auth) {
    return ResponseEntity.ok(this.unbanAccountService.handle(auth.getName(), bannedId));
  }

  @GetMapping("/{bannedId}")
  public ResponseEntity<?> findAccountBans(@PathVariable("bannedId") String bannedId) {
    return ResponseEntity.ok(this.findAccountBansService.handle(bannedId));
  }

}
