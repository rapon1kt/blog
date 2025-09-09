package com.raponi.blog.application.usecase;

public interface PostValidatorUseCase {

  boolean validatePostPresenceAndPrivate(String postId);

}
