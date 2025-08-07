package com.raponi.blog.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.CreateAccountService;
import com.raponi.blog.domain.model.Account;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignUpController {

  @Autowired
  private CreateAccountService createAccountService;

  @PostMapping(value = "/req/signup", consumes = "application/json")
  public Account postMethodName(@RequestBody Account account) {
    return this.createAccountService.handle(account);
  }

}
