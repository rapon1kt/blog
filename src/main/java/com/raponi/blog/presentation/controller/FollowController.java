package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.follow.FollowAndUnfollowAccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/accounts/{accountId}")
public class FollowController {

  private final FollowAndUnfollowAccountService followAndUnfollowAccountService;

  public FollowController(FollowAndUnfollowAccountService followAndUnfollowAccountService) {
    this.followAndUnfollowAccountService = followAndUnfollowAccountService;
  }

  @PostMapping("/follow")
  public ResponseEntity<?> followAndUnfollow(@PathVariable("accountId") String followingId, Authentication auth) {
    return ResponseEntity.ok(this.followAndUnfollowAccountService.handle(auth.getName(), followingId));
  }

}
