package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateAccountUsernameRequestDTO {
  @NotBlank(message = "Use nme is required.")
  @Size(min = 3, message = "Username must be at least 8 characters long.")
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
