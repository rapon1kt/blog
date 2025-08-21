package com.raponi.blog.domain.usecase.follow;

public interface FollowAndUnfollowAccountUseCase {
  public String handle(String followerId, String followingId);
}
