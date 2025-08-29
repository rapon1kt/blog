package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.presentation.protocols.Http;

public interface CreatePostUseCase {
  public Http.PostResponseBody handle(Post postBody, String tokenId);
}
