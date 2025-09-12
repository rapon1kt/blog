package com.raponi.blog.domain.model;

import java.time.Instant;

public class Like {
  String id;
  String accountId;
  String targetId;
  LikeTargetType targetType;
  Instant createdAt;

  public Like(String id, String accountId, String targetId, LikeTargetType targetType, Instant createdAt) {
    this.id = id;
    this.accountId = accountId;
    this.targetId = targetId;
    this.targetType = targetType;
    this.createdAt = createdAt;
  }

  public static Like create(String accountId, String targetId, LikeTargetType type) {
    return new Like(null, accountId, targetId, type, Instant.now());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public LikeTargetType getTargetType() {
    return targetType;
  }

  public void setTargetType(LikeTargetType targetType) {
    this.targetType = targetType;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
