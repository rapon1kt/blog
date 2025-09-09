package com.raponi.blog.application.usecase.post;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface UpdatePostStatusUseCase {
  public PostResponseDTO handle(String accountId, String postId);
}
