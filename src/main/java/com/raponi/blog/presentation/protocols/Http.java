package com.raponi.blog.presentation.protocols;

import java.time.Instant;

public record Http(CommentResponseBody commentResponseBody) {
  public record CommentResponseBody(
      String id,
      String content,
      String accountId,
      String postId,
      Instant createdAt) {

  }
}
