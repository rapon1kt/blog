package com.raponi.blog.application.usecase.post;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface FindPostByIdUseCase {
  public PostResponseDTO handle(String postId);
}
