package com.raponi.blog.presentation.errors;

public class InvalidParamError extends RuntimeException {
  public InvalidParamError(String paramName) {
    super("Invalid param: " + paramName);
  }
}
