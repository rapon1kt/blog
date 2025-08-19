package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.domain.model.Post;

public interface CreatePostUseCase {
  public Post handle(Post postBody);
}
