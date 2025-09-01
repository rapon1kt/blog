package com.raponi.blog.domain.usecase;

public interface PostValidatorUseCase {

  boolean validatePostPresenceAndPrivate(String postId);

}
