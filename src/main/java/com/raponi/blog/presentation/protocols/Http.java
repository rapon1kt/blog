package com.raponi.blog.presentation.protocols;

import java.time.Instant;

public record Http(RegisterBody registerBody, LoginBody loginBody, ResponseBody responseBody,
    UpdateBody updateBody, AuthBody authBody, PostResponseBody postResponseBody,
    CommentResponseBody commentResponseBody) {
  public record RegisterBody(
      String email,
      String username,
      String password) {
  }

  public record LoginBody(
      String username,
      String password) {
  }

  public record ResponseBody(
      String id,
      String username,
      Instant createdAt) {
  }

  public record UpdateBody(
      String username,
      String password,
      String newPassword,
      boolean active) {
  }

  public record AuthBody(
      String password) {

  }

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
