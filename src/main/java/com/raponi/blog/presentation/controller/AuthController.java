package com.raponi.blog.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.CreateAccountService;
import com.raponi.blog.application.service.account.LoginAccountService;
import com.raponi.blog.presentation.dto.request.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.LoginAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.CreatedAccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AccountMapper mapper;
  private final LoginAccountService loginService;
  private final CreateAccountService signUpService;

  public AuthController(
      AccountMapper mapper,
      LoginAccountService loginService,
      CreateAccountService signUpService) {
    this.mapper = mapper;
    this.loginService = loginService;
    this.signUpService = signUpService;
  }

  @PostMapping(value = "/sign-in", consumes = "application/json")
  public ResponseEntity<?> signIn(@RequestBody @Valid LoginAccountRequestDTO requestDTO) {
    var command = this.mapper.toLoginCommand(requestDTO);
    var response = this.loginService.handle(command);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/sign-up", consumes = "application/json")
  public ResponseEntity<CreatedAccountResponseDTO> signUp(@RequestBody @Valid CreateAccountRequestDTO requestDTO) {
    var command = this.mapper.toCreateCommand(requestDTO);
    var account = this.signUpService.handle(command);
    var response = this.mapper.toCreated(account);
    return ResponseEntity.status(201).body(response);
  }

}
