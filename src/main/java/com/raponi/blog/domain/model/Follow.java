package com.raponi.blog.domain.model;

import java.time.Instant;

public record Follow(
    String id,
    String followerId,
    String followingId,
    Instant createdAt) {

  public static Follow create(String followerId, String followingId) {
    return new Follow(null, followerId, followingId, Instant.now());
  }

}
