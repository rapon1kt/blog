package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;

import lombok.Data;

@Data
@Document(collection = "bans")
public class BanEntity {
  @Id
  private String id;
  private BanStatus status;
  private BanCategory category;
  private BanReason reason;
  private String banDescription;
  private String moderatorDescription;
  private String moderatorId;
  private String bannedId;
  private Instant expiresAt;
  private Instant bannedAt;
}
