package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequestDTO {

  @NotBlank(message = "Comments cannot be empty.")
  @Max(value = 100, message = "Comments cannot exceed 100 characters.")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
