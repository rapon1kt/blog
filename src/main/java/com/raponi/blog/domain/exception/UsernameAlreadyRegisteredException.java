package com.raponi.blog.domain.exception;

public class UsernameAlreadyRegisteredException extends RuntimeException {

  public UsernameAlreadyRegisteredException(String message) {
    super(message);
  }
}
