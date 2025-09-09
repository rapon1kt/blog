package com.raponi.blog.application.usecase.follow;

public interface FollowAndUnfollowAccountUseCase {
  public String handle(String followerId, String followingId);
}
