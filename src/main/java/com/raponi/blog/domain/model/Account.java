package com.raponi.blog.domain.model;

import java.time.Instant;

public record Account(
    String id,
    String email,
    String username,
    String picture,
    String description,
    String password,
    boolean active,
    String role,
    Instant createdAt,
    Instant modifiedAt) {

  public static Account create(String email, String username, String hashedPassword) {
    Instant instNow = Instant.now();
    return new Account(
        null,
        email,
        username,
        null,
        null,
        hashedPassword,
        true,
        "USER",
        instNow,
        instNow);
  }

  public Account changePassword(String newPassword) {
    return new Account(id, email, username, picture, description, newPassword, active,
        role, createdAt, Instant.now());
  }

  public Account changeStatus() {
    return new Account(id, email, username, picture, description, password,
        active ? false : true, role, createdAt, Instant.now());
  }

  public Account updateAccountInfos(String newUsername, String newPicture, String newDescription) {
    return new Account(
        id,
        email,
        newUsername,
        newPicture,
        newDescription,
        password,
        active,
        role,
        createdAt,
        Instant.now());
  }

}
