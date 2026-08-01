package com.raponi.blog.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCommentRequestDTO {

  @NotBlank(message = "Comments cannot be empty.")
  @Size(max = 100, message = "Comments cannot exceed 100 characters.")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
