package com.raponi.blog.domain.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }
}
