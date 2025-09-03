package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequestDTO {

  @NotBlank(message = "Comments cannot be empty.")
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
