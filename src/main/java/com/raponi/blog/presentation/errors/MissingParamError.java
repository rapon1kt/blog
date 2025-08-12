package com.raponi.blog.presentation.errors;

public class MissingParamError extends RuntimeException {
  public MissingParamError(String paramName) {
    super("Missing param: " + paramName);
  }
}
