package com.raponi.blog.application.result;

import com.raponi.blog.domain.model.Account;

public class SignInAccountResult {

  private String token;
  private Account account;

  public SignInAccountResult(Account account, String token) {
    this.token = token;
    this.account = account;
  }

  public String getToken() {
    return token;
  }

  public Account getAccount() {
    return account;
  }

}