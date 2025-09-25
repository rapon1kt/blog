package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.NotificationType;

import lombok.Data;

@Data
@Document(collection = "notifications")
public class NotificationEntity {
  @Id
  private String id;
  private String authorId;
  private String actorId;
  private NotificationType type;
  private boolean read;
  private String targetId;
  private Instant createdAt;
}
