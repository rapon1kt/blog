package com.raponi.blog.application.usecase.comment;

import java.util.List;

import com.raponi.blog.presentation.dto.CommentResponseDTO;

public interface FindAllCommentAnswersUseCase {
  List<CommentResponseDTO> handle(String commentId);
}
