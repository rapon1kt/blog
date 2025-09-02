package com.raponi.blog.presentation.errors;

public class MissingParamException extends RuntimeException {
  public MissingParamException(String paramName) {
    super("Missing param: " + paramName);
  }
}
