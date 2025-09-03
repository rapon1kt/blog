package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface FindPostByIdUseCase {
  public PostResponseDTO handle(String postId);
}
