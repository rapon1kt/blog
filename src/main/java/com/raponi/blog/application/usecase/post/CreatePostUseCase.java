package com.raponi.blog.application.usecase.post;

import com.raponi.blog.domain.model.Post;

public interface CreatePostUseCase {
  public Post handle(CreatePostCommand command, String tokenId);
}
