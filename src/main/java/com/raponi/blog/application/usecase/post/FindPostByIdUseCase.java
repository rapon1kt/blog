package com.raponi.blog.application.usecase.post;

import com.raponi.blog.domain.model.Post;

public interface FindPostByIdUseCase {
  public Post handle(String postId);
}
