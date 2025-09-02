package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateAccountPasswordRequestDTO {

  @NotBlank(message = "Password is required.")
  private String password;

  @NotBlank(message = "New password cannot be blank.")
  private String newPassword;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

}
