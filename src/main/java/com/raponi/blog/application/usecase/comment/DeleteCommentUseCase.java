package com.raponi.blog.application.usecase.comment;

public interface DeleteCommentUseCase {
  String handle(String accountId, String commentId, String postId);
}
