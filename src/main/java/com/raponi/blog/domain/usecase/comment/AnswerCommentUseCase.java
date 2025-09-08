package com.raponi.blog.domain.usecase.comment;

import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;

public interface AnswerCommentUseCase {
  public CommentResponseDTO handle(String accountId, String commentId, CreateCommentRequestDTO requestDTO);
}
