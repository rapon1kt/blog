package com.raponi.blog.presentation.errors;

public class InternalServerException extends RuntimeException {
  public InternalServerException(String message) {
    super(message);
  }
}
