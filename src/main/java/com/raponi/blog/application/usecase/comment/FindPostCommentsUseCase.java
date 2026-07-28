package com.raponi.blog.application.usecase.comment;

import java.util.List;

import com.raponi.blog.domain.model.Comment;

public interface FindPostCommentsUseCase {
  List<Comment> handle(String postId);
}
