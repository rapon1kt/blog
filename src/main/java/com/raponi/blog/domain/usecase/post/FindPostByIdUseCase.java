package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.domain.model.Post;

public interface FindPostByIdUseCase {
  public Post handle(String postId, String accountId);
}
