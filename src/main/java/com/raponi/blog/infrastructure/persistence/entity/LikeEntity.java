package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.LikeTargetType;

import lombok.Data;

@Data
@Document(collection = "likes")
public class LikeEntity {
  @Id
  private String id;
  private String accountId;
  private String targetId;
  private LikeTargetType targetType;
  private Instant createdAt;

}
