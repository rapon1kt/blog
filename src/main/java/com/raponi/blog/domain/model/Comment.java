package com.raponi.blog.domain.model;

import java.time.Instant;

import com.raponi.blog.presentation.protocols.Http;

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

  public Http.CommentResponseBody toResponseBody() {
    return new Http.CommentResponseBody(id, content, accountId, postId, createdAt);
  }
}
