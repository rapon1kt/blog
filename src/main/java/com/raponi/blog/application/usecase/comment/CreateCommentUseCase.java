package com.raponi.blog.application.usecase.comment;

import com.raponi.blog.domain.model.Comment;

public interface CreateCommentUseCase {
  Comment handle(String accountId, String postId, CreateCommentCommand command);
}
