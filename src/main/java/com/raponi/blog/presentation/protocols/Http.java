package com.raponi.blog.presentation.protocols;

public record Http(RegisterBody registerBody, LoginBody loginBody) {
  public record RegisterBody(
      String email,
      String username,
      String password) {
  }

  public record LoginBody(
      String username,
      String password) {
  }
}
