package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.CreateAccountService;
import com.raponi.blog.presentation.dto.CreateAccountRequestDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignUpController {

  private final CreateAccountService createAccountService;

  public SignUpController(CreateAccountService createAccountService, AccountMapper accountMapper) {
    this.createAccountService = createAccountService;
  }

  @PostMapping(value = "/req/signup", consumes = "application/json")
  public ResponseEntity<?> signUp(@RequestBody @Valid CreateAccountRequestDTO request) {
    return ResponseEntity.status(201).body(this.createAccountService.handle(request));
  }

}
