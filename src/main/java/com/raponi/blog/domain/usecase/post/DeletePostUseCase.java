package com.raponi.blog.domain.usecase.post;

public interface DeletePostUseCase {
  public String handle(String accountId, String postId);
}
