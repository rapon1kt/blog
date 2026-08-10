package com.raponi.blog.presentation.dto.response;

import java.time.Instant;

public class SignUpAccountResponseDTO {

  private String id;
  private String email;
  private String username;
  private Instant createdAt;

  public SignUpAccountResponseDTO(
      String id,
      String email,
      String username,
      Instant createdAt) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.createdAt = createdAt;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

}
