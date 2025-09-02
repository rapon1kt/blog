package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAccountRequestDTO {

  @NotBlank(message = "Username is required.")
  @Size(min = 3, message = "Username must be at least 3 characteres long.")
  private String username;

  @NotBlank(message = "Email is required.")
  @Email(message = "Email format is incorrect.")
  private String email;

  @NotBlank(message = "Password is required.")
  @Size(min = 8, message = "Password must be at least 8 charactres long.")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
