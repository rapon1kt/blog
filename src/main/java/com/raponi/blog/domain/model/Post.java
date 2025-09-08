package com.raponi.blog.domain.model;

import java.time.Instant;

public class Post {
  private String id;
  private String title;
  private String content;
  private boolean privateStatus;
  private String accountId;
  private Instant createdAt;
  private Instant modifiedAt;

  private Post(String id, String title, String content, boolean privateStatus, String accountId, Instant createdAt,
      Instant modifiedAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.privateStatus = privateStatus;
    this.accountId = accountId;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Post create(String accountId, String title, String content) {
    Instant instant = Instant.now();
    return new Post(null, title, content, false, accountId, instant, instant);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isPrivateStatus() {
    return privateStatus;
  }

  public void setPrivateStatus(boolean privateStatus) {
    this.privateStatus = privateStatus;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
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
