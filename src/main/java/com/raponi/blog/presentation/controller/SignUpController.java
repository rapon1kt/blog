package com.raponi.blog.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.account.CreateAccountService;
import com.raponi.blog.presentation.helpers.HttpHelper;
import com.raponi.blog.presentation.protocols.Http;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SignUpController {

  @Autowired
  private CreateAccountService createAccountService;

  @PostMapping(value = "/req/signup", consumes = "application/json")
  public ResponseEntity<?> signUp(@RequestBody Http.Body requestBody) {
    try {
      return HttpHelper.ok(this.createAccountService.handle(requestBody));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}
