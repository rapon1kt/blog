package com.raponi.blog.application.usecase.post;

public interface DeletePostUseCase {
  public String handle(String accountId, String postId);
}
