package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpAccountRequestDTO {
  @NotBlank(message = "Email is required.")
  @Email(message = "Please provide a valid email format.")
  private String email;

  @NotBlank(message = "Username is required.")
  @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters long.")
  private String username;

  @NotBlank(message = "Password is required.")
  @Size(min = 8, max = 20, message = "The password must be between 8 and 20 characteres long.")
  private String password;

  public SignUpAccountRequestDTO(String email, String username, String password) {
    this.email = email;
    this.username = username;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
