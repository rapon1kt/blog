package com.raponi.blog.domain.usecase.like;

public interface CountPostLikeUseCase {
  long handle(String postId);
}
