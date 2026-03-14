package com.raponi.blog.presentation.dto;

public class LoginAccountResponseDTO {
  private PublicAccountResponseDTO user;
  private String token;

  public PublicAccountResponseDTO getUser() {
    return user;
  }

  public void setUser(PublicAccountResponseDTO user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
