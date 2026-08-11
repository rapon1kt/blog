package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignInAccountRequestDTO {

  @NotBlank(message = "Email is required.")
  @Email(message = "Email must be in a valid format.")
  private String email;

  @NotBlank(message = "Email is required.")
  private String password;

  public SignInAccountRequestDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

}
