package com.raponi.blog.presentation.errors;

public class NotFoundError extends RuntimeException {
  public NotFoundError(String message) {
    super(message + " cannot be found.");
  }
}
