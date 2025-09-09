package com.raponi.blog.application.usecase.post;

import java.util.List;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface FindAllPostsUseCase {
  public List<PostResponseDTO> handle();
}
