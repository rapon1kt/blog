package com.raponi.blog.presentation.protocols;

public record Http(RegisterBody registerBody, LoginBody loginBody, UpdateBody updateBody) {
  public record RegisterBody(
      String email,
      String username,
      String password) {
  }

  public record LoginBody(
      String username,
      String password) {
  }

  public record UpdateBody(
      String username,
      String password,
      boolean active) {
  }
}
