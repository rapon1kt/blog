package com.raponi.blog.domain.usecase.post;

import com.raponi.blog.presentation.protocols.Http;

public interface UpdatePostStatusUseCase {
  public Http.PostResponseBody handle(String accountId, String postId);
}
