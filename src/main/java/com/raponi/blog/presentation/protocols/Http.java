package com.raponi.blog.presentation.protocols;

import java.time.Instant;

public record Http(PostResponseBody postResponseBody,
    CommentResponseBody commentResponseBody) {

  public record PostResponseBody(
      String postId,
      String title,
      String content,
      String accountId,
      Instant createdAt) {

  }

  public record CommentResponseBody(
      String id,
      String content,
      String accountId,
      String postId,
      Instant createdAt) {

  }
}
