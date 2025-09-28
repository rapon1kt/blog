package com.raponi.blog.application.usecase.post;

public interface PinPostUseCase {
  public void handle(String accountId, String postId);
}
