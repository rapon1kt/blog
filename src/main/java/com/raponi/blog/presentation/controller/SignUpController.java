package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.account.CreateAccountService;
import com.raponi.blog.presentation.dto.request.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.CreatedAccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SignUpController {

  private final CreateAccountService createAccountService;
  private final AccountMapper mapper;

  public SignUpController(CreateAccountService createAccountService, AccountMapper mapper) {
    this.createAccountService = createAccountService;
    this.mapper = mapper;
  }

  @PostMapping(value = "/req/signup", consumes = "application/json")
  public ResponseEntity<CreatedAccountResponseDTO> signUp(@RequestBody @Valid CreateAccountRequestDTO requestDTO) {
    var command = mapper.toCreateCommand(requestDTO);
    var account = createAccountService.handle(command);
    var response = mapper.toCreated(account);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

    return ResponseEntity.created(location).body(response);
  }
}
