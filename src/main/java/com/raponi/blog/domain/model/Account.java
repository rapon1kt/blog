package com.raponi.blog.domain.model;

import java.time.Instant;

import com.raponi.blog.presentation.protocols.Http;

public record Account(
    String id,
    String email,
    String username,
    String password,
    boolean active,
    Instant createdAt,
    Instant modifiedAt) {

  public static Account create(String email, String username, String hashedPassword) {
    Instant instNow = Instant.now();
    return new Account(
        null,
        email,
        username,
        hashedPassword,
        true,
        instNow,
        instNow);
  }

  public Account changePassword(String newPassword) {
    return new Account(id, email, username, newPassword, active, createdAt, Instant.now());
  }

  public Account changeStatus(boolean activeStatus) {
    return new Account(id, email, username, password, activeStatus, createdAt, Instant.now());
  }

  public Account update(String newUsername) {
    return new Account(
        id,
        email,
        newUsername,
        password,
        active,
        createdAt,
        Instant.now());
  }

  public Http.ResponseBody toResponseBody() {
    return new Http.ResponseBody(
        id,
        email,
        username,
        active,
        createdAt,
        modifiedAt);
  }
}
