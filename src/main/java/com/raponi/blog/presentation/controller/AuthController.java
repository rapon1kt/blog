package com.raponi.blog.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.SignInAccountService;
import com.raponi.blog.application.service.SignUpAccountService;
import com.raponi.blog.presentation.dto.request.SignInAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.SignUpAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.SignInAccountResponseDTO;
import com.raponi.blog.presentation.dto.response.SignUpAccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountPresentationMapper;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping(path = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  private final AccountPresentationMapper mapper;
  private final SignUpAccountService signUpService;
  private final SignInAccountService signInService;

  public AuthController(
      AccountPresentationMapper mapper,
      SignUpAccountService signUpService,
      SignInAccountService signInService) {
    this.mapper = mapper;
    this.signUpService = signUpService;
    this.signInService = signInService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpAccountResponseDTO> signUp(@RequestBody @Valid SignUpAccountRequestDTO requestDTO) {
    var command = mapper.toSignUpCommand(requestDTO);
    var account = signUpService.handle(command);
    var response = mapper.toSignUpResponse(account);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<SignInAccountResponseDTO> signIn(@RequestBody @Valid SignInAccountRequestDTO requestDTO) {
    var command = mapper.toSignInCommand(requestDTO);
    var result = signInService.handle(command);
    var response = new SignInAccountResponseDTO(result);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
