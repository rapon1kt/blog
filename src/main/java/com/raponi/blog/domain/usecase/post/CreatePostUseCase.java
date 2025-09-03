package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.presentation.dto.CreatePostRequestDTO;
import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface CreatePostUseCase {
  public PostResponseDTO handle(CreatePostRequestDTO requestDTO, String tokenId);
}
