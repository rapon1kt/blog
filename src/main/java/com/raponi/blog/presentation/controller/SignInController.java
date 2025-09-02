package com.raponi.blog.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.LoginAccountService;
import com.raponi.blog.presentation.dto.LoginAccountRequestDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignInController {

  @Autowired
  private LoginAccountService loginAccountService;

  @PostMapping(value = "/req/login", consumes = "application/json")
  public ResponseEntity<?> signIn(@RequestBody LoginAccountRequestDTO requestDTO) {
    return ResponseEntity.ok(this.loginAccountService.handle(requestDTO));
  }

}
