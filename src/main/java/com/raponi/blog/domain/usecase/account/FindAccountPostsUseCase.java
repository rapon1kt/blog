package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.presentation.dto.PostResponseDTO;

public interface FindAccountPostsUseCase {
  public List<PostResponseDTO> handle(String accountId);
}
