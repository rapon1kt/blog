package com.raponi.blog.domain.model;

import java.time.Instant;

public record Comment(
    String id,
    String content,
    String accountId,
    String postId,
    Instant createdAt,
    Instant modifiedAt) {

  public static Comment create(String content, String accountId, String postId) {
    Instant instant = Instant.now();
    return new Comment(null, content, accountId, postId, instant, instant);
  }

}
