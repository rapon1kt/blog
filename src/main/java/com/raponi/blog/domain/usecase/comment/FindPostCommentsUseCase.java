package com.raponi.blog.domain.usecase.comment;

import java.util.List;

import com.raponi.blog.presentation.protocols.Http;

public interface FindPostCommentsUseCase {
  List<Http.CommentResponseBody> handle(String postId);
}
