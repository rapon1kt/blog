package com.raponi.blog.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.SignUpAccountService;
import com.raponi.blog.presentation.dto.request.SignUpAccountRequestDTO;
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

  public AuthController(AccountPresentationMapper mapper, SignUpAccountService signUpService) {
    this.mapper = mapper;
    this.signUpService = signUpService;
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpAccountResponseDTO> signUp(@RequestBody @Valid SignUpAccountRequestDTO requestDTO) {
    var command = mapper.toCommand(requestDTO);
    var account = signUpService.handle(command);
    var response = mapper.toResponse(account);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
