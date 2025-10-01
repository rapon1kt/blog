package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;

import lombok.Data;

@Data
@Document(collection = "bans")
public class BanEntity {
  @Id
  private String id;
  private boolean active;
  private BanCategory category;
  private BanReason reason;
  private String description;
  private String moderatorId;
  private String bannedId;
  private Instant expiresAt;
  private Instant bannedAt;
}
