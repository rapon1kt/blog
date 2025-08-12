package com.raponi.blog.presentation.errors;

public class AccountNotFound extends RuntimeException {
  public AccountNotFound(String message) {
    super("Account with " + message + " cannot be found.");
  }
}
