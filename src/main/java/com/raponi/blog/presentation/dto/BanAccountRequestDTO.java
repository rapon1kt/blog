package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BanAccountRequestDTO {

  @NotBlank(message = "Description is required!")
  private String description;

  @NotNull(message = "Time of ban (in days) is required")
  private long time;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

}
