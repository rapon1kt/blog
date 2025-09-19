package com.raponi.blog.domain.model;

import java.time.Instant;

public class Comment implements Reportable, Likable {
  private String id;
  private String content;
  private String authorId;
  private String postId;
  private String commentId;
  private boolean answer;
  private long likeCount;
  private Instant createdAt;
  private Instant modifiedAt;

  public Comment(String id, String content, String authorId, String postId, String commentId, boolean answer,
      Instant createdAt,
      Instant modifiedAt) {
    this.id = id;
    this.content = content;
    this.authorId = authorId;
    this.postId = postId;
    this.commentId = commentId;
    this.answer = answer;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Comment create(String authorId, String postId, String content) {
    Instant instant = Instant.now();
    return new Comment(null, content, authorId, postId, null, false, instant, instant);
  }

  @Override
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

  @Override
  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public boolean isAnswer() {
    return answer;
  }

  public void setAnswer(boolean answer) {
    this.answer = answer;
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
