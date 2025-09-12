package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "likes")
public class LikeEntity {
  @Id
  private String id;
  private String postId;
  private String accountId;
  private Instant createdAt;

}
