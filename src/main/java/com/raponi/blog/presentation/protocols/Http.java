package com.raponi.blog.presentation.protocols;

import java.time.Instant;

public record Http(RegisterBody registerBody, LoginBody loginBody, ResponseBody responseBody) {
  public record RegisterBody(
      String email,
      String username,
      String password) {
  }

  public record LoginBody(
      String username,
      String password) {
  }

  public record ResponseBody(
      String id,
      String email,
      String username,
      boolean active,
      Instant createdAt,
      Instant modifiedAt) {
  }
}
