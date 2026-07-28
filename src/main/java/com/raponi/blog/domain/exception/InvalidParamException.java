package com.raponi.blog.domain.exception;

public class InvalidParamException extends RuntimeException {

  public InvalidParamException(String message) {
    super(message);
  }
}
