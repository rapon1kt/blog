package com.raponi.blog.domain.model;

import java.time.Instant;

public record Like(
    String id,
    String accountId,
    String postId,
    Instant createdAt) {

  public static Like create(String accountId, String postId) {
    return new Like(null, accountId, postId, Instant.now());
  }

}
