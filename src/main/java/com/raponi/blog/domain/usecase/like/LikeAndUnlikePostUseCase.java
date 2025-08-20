package com.raponi.blog.domain.usecase.like;

public interface LikeAndUnlikePostUseCase {
  String handle(String postId, String accountId);
}
