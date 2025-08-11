package com.raponi.blog.domain.model;

import java.time.Instant;

public record Account(
    String id,
    String email,
    String username,
    String password,
    Instant createdAt,
    Instant modifiedAt) {

  public static Account create(String email, String username, String hashedPassword) {
    Instant instNow = Instant.now();
    return new Account(
        null,
        email,
        username,
        hashedPassword,
        instNow,
        instNow);
  }

  public Account changePassword(String newPassword) {
    return new Account(id, email, username, newPassword, createdAt, Instant.now());
  }

  public Account update(String newUsername) {
    return new Account(
        id,
        email,
        newUsername,
        password,
        createdAt,
        Instant.now());
  }
}
