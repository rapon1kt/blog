package com.raponi.blog.presentation.errors;

public class InvalidParamException extends RuntimeException {
  public InvalidParamException(String message) {
    super(message);
  }
}
