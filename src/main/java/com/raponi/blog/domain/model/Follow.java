package com.raponi.blog.domain.model;

import java.time.Instant;

public class Follow {
  String id;
  String followerId;
  String followingId;
  Instant createdAt;

  public Follow(String id, String followerId, String followingId, Instant createdAt) {
    this.id = id;
    this.followerId = followerId;
    this.followingId = followingId;
    this.createdAt = createdAt;
  }

  public static Follow create(String followerId, String followingId) {
    return new Follow(null, followerId, followingId, Instant.now());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFollowerId() {
    return followerId;
  }

  public void setFollowerId(String followerId) {
    this.followerId = followerId;
  }

  public String getFollowingId() {
    return followingId;
  }

  public void setFollowingId(String followingId) {
    this.followingId = followingId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
