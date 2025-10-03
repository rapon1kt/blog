package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.ban.BanAccountService;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.presentation.dto.BanAccountRequestDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/bans")
@PreAuthorize("hasRole('ADMIN')")
public class BanController {

  private final BanAccountService banAccountService;

  public BanController(BanAccountService banAccountService) {
    this.banAccountService = banAccountService;
  }

  @PostMapping("/{bannedId}")
  public ResponseEntity<?> banAccount(@PathVariable("bannedId") String bannedId,
      @RequestParam("reason") BanReason reason, Authentication auth, @RequestBody BanAccountRequestDTO requestDTO) {
    return ResponseEntity.ok(this.banAccountService.handle(auth.getName(), bannedId, reason, requestDTO));
  }

}
