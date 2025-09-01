package com.raponi.blog.domain.usecase.comment;

public interface DeleteCommentUseCase {
  String handle(String commentId, String postId);
}
