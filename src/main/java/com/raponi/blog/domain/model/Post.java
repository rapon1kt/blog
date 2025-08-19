package com.raponi.blog.domain.model;

import java.time.Instant;

public record Post(
    String id,
    String title,
    String content,
    boolean privateStatus,
    String accountId,
    Instant createdAt,
    Instant modifiedAt) {

  public static Post create(String accountId, String title, String content) {
    Instant instant = Instant.now();
    return new Post(null, title, content, false, accountId, instant, instant);
  }

  public Post changeStatus(boolean newStatus) {
    return new Post(
        id,
        title,
        content,
        newStatus,
        accountId,
        createdAt,
        Instant.now());
  }

}
