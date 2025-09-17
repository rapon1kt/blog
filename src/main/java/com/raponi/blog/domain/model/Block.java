package com.raponi.blog.domain.model;

import java.time.Instant;

public class Block {
  private String id;
  private String blockerId;
  private String blockedId;
  private Instant createdAt;

  public Block(String blockerId, String blockedId) {
    this.blockerId = blockerId;
    this.blockedId = blockedId;
    this.createdAt = Instant.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBlockerId() {
    return blockerId;
  }

  public void setBlockerId(String blockerId) {
    this.blockerId = blockerId;
  }

  public String getBlockedId() {
    return blockedId;
  }

  public void setBlockedId(String blockedId) {
    this.blockedId = blockedId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
