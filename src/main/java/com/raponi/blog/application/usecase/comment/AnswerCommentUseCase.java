package com.raponi.blog.application.usecase.comment;

import com.raponi.blog.domain.model.Comment;

public interface AnswerCommentUseCase {
  public Comment handle(String accountId, String commentId, CreateCommentCommand command);
}
