package com.raponi.blog.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePostRequestDTO {

  @NotBlank(message = "Title is required.")
  @Size(min = 4, message = "Title must be at least 3 characters long.")
  private String title;

  @NotBlank(message = "Content is required.")
  @Size(min = 50, message = "Content must be at least 50 characters long.")
  private String content;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
