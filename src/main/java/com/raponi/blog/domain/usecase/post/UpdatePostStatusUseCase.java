package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface UpdatePostStatusUseCase {
  public PostResponseDTO handle(String accountId, String postId);
}
