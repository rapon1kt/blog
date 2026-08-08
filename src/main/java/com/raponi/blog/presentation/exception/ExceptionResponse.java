package com.raponi.blog.presentation.exception;

public class ExceptionResponse {

  private int statusCode;
  private String message;

  public ExceptionResponse(String message) {
    super();
    this.message = message;
  }

  public ExceptionResponse(int statusCode, String message) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
