package com.raponi.blog.domain.usecase.comment;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.presentation.protocols.Http;

public interface CreateCommentUseCase {
  Http.CommentResponseBody handle(String accountId, String postId, Comment comment);
}
