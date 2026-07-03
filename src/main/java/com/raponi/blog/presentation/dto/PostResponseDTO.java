package com.raponi.blog.presentation.dto;

import java.time.Instant;

import com.raponi.blog.domain.model.PostVisibility;

public class PostResponseDTO {
  private String id;
  private String title;
  private String content;
  private String authorId;
  private PostVisibility postVisibility;
  private boolean pinned;
  private Instant createdAt;
  private Instant modifiedAt;
  private long likeCount;

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

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public PostVisibility getPostVisibility() {
    return postVisibility;
  }

  public void setPostVisibility(PostVisibility postVisibility) {
    this.postVisibility = postVisibility;
  }

  public boolean isPinned() {
    return pinned;
  }

  public void setPinned(boolean pinned) {
    this.pinned = pinned;
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

  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }

}
