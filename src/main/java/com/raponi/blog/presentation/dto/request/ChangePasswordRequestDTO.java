package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequestDTO {

  @NotBlank(message = "Password is required.")
  private String password;

  @NotBlank(message = "New password cannot be blank.")
  @Size(min = 8, max = 20, message = "New password must be at least 8 charactres long.")
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
