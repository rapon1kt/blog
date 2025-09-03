package com.raponi.blog.domain.usecase.comment;

import java.util.List;

import com.raponi.blog.presentation.dto.CommentResponseDTO;

public interface FindPostCommentsUseCase {
  List<CommentResponseDTO> handle(String postId);
}
