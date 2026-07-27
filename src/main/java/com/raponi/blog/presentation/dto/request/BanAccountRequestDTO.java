package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BanAccountRequestDTO {
  
  
  @NotBlank(message = "A description (reason) for the ban is required.")
  @Size(min = 20, max = 100, message = "Please describe the reason for the ban in 20 to 50 characters.")
  private String description;

  @NotNull(message = "A time (in days) for the ban is required.")
  @Min(value = 1, message = "The ban duration must be longer than one day.")
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
