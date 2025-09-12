package com.raponi.blog.domain.model;

import java.time.Instant;

public class Like {
  String id;
  String accountId;
  String postId;
  Instant createdAt;

  public Like(String id, String accountId, String postId, Instant createdAt) {
    this.id = id;
    this.accountId = accountId;
    this.postId = postId;
    this.createdAt = createdAt;
  }

  public static Like create(String accountId, String postId) {
    return new Like(null, accountId, postId, Instant.now());
  }

}
