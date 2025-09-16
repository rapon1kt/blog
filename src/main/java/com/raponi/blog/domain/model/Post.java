package com.raponi.blog.domain.model;

import java.time.Instant;

public class Post implements Reportable, Likable {
  private String id;
  private String title;
  private String content;
  private PostVisibility postVisibility;
  private long likeCount;
  private String accountId;
  private Instant createdAt;
  private Instant modifiedAt;

  public Post(String id, String title, String content, PostVisibility postVisibility, String accountId,
      Instant createdAt,
      Instant modifiedAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.postVisibility = postVisibility;
    this.accountId = accountId;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Post create(String accountId, String title, String content) {
    Instant instant = Instant.now();
    return new Post(null, title, content, PostVisibility.PUBLIC, accountId, instant, instant);
  }

  @Override
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

  public PostVisibility getPostVisibility() {
    return postVisibility;
  }

  public void setPostVisibility(PostVisibility postVisibility) {
    this.postVisibility = postVisibility;
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

  @Override
  public String getAuthorId() {
    return accountId;
  }

  @Override
  public String getContentPreview() {
    return content.length() > 50 ? content.substring(0, 50) + "..." : content;
  }

  @Override
  public long getLikeCount() {
    return this.likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }

  @Override
  public void incrementLikeCount() {
    this.setLikeCount(this.likeCount + 1);
  }

  @Override
  public void decrementLikeCount() {
    if (this.likeCount > 0)
      this.setLikeCount(this.likeCount - 1);
  }

}
