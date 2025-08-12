package com.raponi.blog.presentation.protocols;

public record Http(Body body) {
  public record Body(
      String email,
      String username,
      String password) {
  }
}
