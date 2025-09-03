package com.raponi.blog.domain.usecase.post;

import java.util.List;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface FindAllPostsUseCase {
  public List<PostResponseDTO> handle();
}
