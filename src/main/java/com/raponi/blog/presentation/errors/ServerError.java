package com.raponi.blog.presentation.errors;

public class ServerError extends RuntimeException {
  public ServerError() {
    super("Internal Server Error");
  }
}
