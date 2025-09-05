package com.raponi.blog.presentation.dto;

public class UpdateAccountInfosRequestDTO {

  private String username;

  private String profileDescription;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getProfileDescription() {
    return profileDescription;
  }

  public void setProfileDescription(String profileDescription) {
    this.profileDescription = profileDescription;
  }

}
