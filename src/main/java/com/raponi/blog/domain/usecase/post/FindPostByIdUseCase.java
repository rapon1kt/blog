package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.presentation.protocols.Http;

public interface FindPostByIdUseCase {
  public Http.PostResponseBody handle(String postId);
}
