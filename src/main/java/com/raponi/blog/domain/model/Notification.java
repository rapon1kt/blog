package com.raponi.blog.domain.model;

import java.time.Instant;

public class Notification {
  private String id;
  private String authorId;
  private String actorId;
  private NotificationType type;
  private boolean read;
  private String targetId;
  private Instant createdAt;

  public Notification(String id, String authorId, String actorId, NotificationType type, String targetId) {
    this.id = id;
    this.authorId = authorId;
    this.actorId = actorId;
    this.type = type;
    this.read = false;
    this.targetId = targetId;
    this.createdAt = Instant.now();
  }

  public static Notification create(String authorId, String actorId, NotificationType type,
      String targetId) {
    return new Notification(null, authorId, actorId, type, targetId);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getActorId() {
    return actorId;
  }

  public void setActorId(String actorId) {
    this.actorId = actorId;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
