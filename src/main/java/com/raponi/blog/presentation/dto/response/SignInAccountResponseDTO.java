package com.raponi.blog.presentation.dto.response;

import com.raponi.blog.application.result.SignInAccountResult;

public class SignInAccountResponseDTO {

  private String token;
  private SignedAccount account;

  public SignInAccountResponseDTO(SignInAccountResult result) {
    this.token = result.getToken();
    this.account = new SignedAccount(
        result.getAccount().getId(),
        result.getAccount().getRole().toString(),
        result.getAccount().getEmail(),
        result.getAccount().getUsername());
  }

  public String getToken() {
    return token;
  }

  public SignedAccount getSignedAccount() {
    return account;
  }

}

record SignedAccount(String id, String role, String email, String username) {
}
