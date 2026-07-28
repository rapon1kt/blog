package com.raponi.blog.application.usecase.post;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;

public interface UpdatePostStatusUseCase {
  public Post handle(String accountId, String postId, PostVisibility newVisibility);
}
