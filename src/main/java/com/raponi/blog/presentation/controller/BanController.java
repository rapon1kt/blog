package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.ban.BanAccountService;
import com.raponi.blog.application.service.ban.FindAccountBansService;
import com.raponi.blog.application.service.ban.FindBansService;
import com.raponi.blog.application.service.ban.UnbanAccountService;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.presentation.dto.request.BanAccountRequestDTO;
import com.raponi.blog.presentation.mapper.BanMapper;

import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bans")
@PreAuthorize("hasRole('ADMIN')")
public class BanController {

  private final BanMapper mapper;
  private final FindBansService findBansService;
  private final BanAccountService banAccountService;
  private final UnbanAccountService unbanAccountService;
  private final FindAccountBansService findAccountBansService;

  public BanController(
    BanMapper mapper,
    FindBansService findBansService,
    BanAccountService banAccountService,
    UnbanAccountService unbanAccountService,
    FindAccountBansService findAccountBansService
  ) {
    this.mapper = mapper;
    this.findBansService = findBansService;
    this.banAccountService = banAccountService;
    this.unbanAccountService = unbanAccountService;
    this.findAccountBansService = findAccountBansService;
  }

  @PostMapping("/{bannedId}")
  public ResponseEntity<Ban> banAccount(
    @PathVariable("bannedId") String bannedId,
    @RequestParam("reason") BanReason reason,
    Authentication auth,
    @RequestBody @Valid BanAccountRequestDTO requestDTO
  ) {
    var command = mapper.tCommand(requestDTO);
    var response = this.banAccountService.handle(auth.getName(), bannedId, reason, command);
    return ResponseEntity.status(201).body(response);
  }

  @PatchMapping("/{bannedId}")
  public ResponseEntity<?> unBanAccount(@PathVariable("bannedId") String bannedId, Authentication auth) {
    return ResponseEntity.ok(this.unbanAccountService.handle(auth.getName(), bannedId));
  }

  @GetMapping("/{bannedId}")
  public ResponseEntity<?> findAccountBans(@PathVariable("bannedId") String bannedId) {
    return ResponseEntity.ok(this.findAccountBansService.handle(bannedId));
  }

  @GetMapping
  public ResponseEntity<?> findAccountBansByReasonAndStatus(@RequestParam Optional<BanReason> reason, @RequestParam Optional<BanStatus> status) {
    return ResponseEntity.ok(this.findBansService.handle(reason, status));
  }
}
