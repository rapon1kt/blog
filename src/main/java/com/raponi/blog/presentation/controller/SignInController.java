package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.account.LoginAccountService;
import com.raponi.blog.presentation.dto.request.LoginAccountRequestDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInController {

  private LoginAccountService loginAccountService;
  private AccountMapper mapper;

  public SignInController(LoginAccountService loginAccountService, AccountMapper mapper) {
    this.loginAccountService = loginAccountService;
    this.mapper = mapper;
  }

  @PostMapping(value = "/req/sign-in", consumes = "application/json")
  public ResponseEntity<?> signIn(@RequestBody LoginAccountRequestDTO requestDTO) {
    var command = mapper.toLoginCommand(requestDTO);
    var response = this.loginAccountService.handle(command);
    return ResponseEntity.ok(response);
  }
}
