package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.domain.model.Post;

public interface UpdatePostStatusUseCase {
  public Post handle(String accountId, String postId, String tokenId);
}
