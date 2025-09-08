package com.raponi.blog.domain.model;

import java.time.Instant;

public class Comment {
  private String id;
  private String content;
  private String accountId;
  private String postId;
  private Instant createdAt;
  private Instant modifiedAt;

  public Comment(String id, String content, String accountId, String postId, Instant createdAt, Instant modifiedAt) {
    this.id = id;
    this.content = content;
    this.accountId = accountId;
    this.postId = postId;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Comment create(String accountId, String postId, String content) {
    Instant instant = Instant.now();
    return new Comment(null, content, accountId, postId, instant, instant);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

}
