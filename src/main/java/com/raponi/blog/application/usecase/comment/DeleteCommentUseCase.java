package com.raponi.blog.application.usecase.comment;

public interface DeleteCommentUseCase {
  String handle(String commentId, String postId);
}
