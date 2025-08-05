package com.raponi.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.service.AccountService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignUpController {

  @Autowired
  private AccountService accountService;

  @PostMapping(value = "/req/signup", consumes = "application/json")
  public Account postMethodName(@RequestBody Account account) {
    return this.accountService.createNewAccount(account);
  }

}
