package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "blocks")
public class BlockEntity {
  @Id
  private String id;
  private String blockerId;
  private String blockedId;
  private Instant createdAt;

}
