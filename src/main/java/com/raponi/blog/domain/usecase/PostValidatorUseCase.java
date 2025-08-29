package com.raponi.blog.domain.usecase;

import com.raponi.blog.domain.model.Post;

public interface PostValidatorUseCase {

  Post validatePostPresenceAndPrivate(String postId);

}
