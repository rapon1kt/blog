package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Like;

import lombok.Data;

@Data
@Document(collection = "likes")
public class LikeEntity {
  @Id
  private String id;
  private String postId;
  private String accountId;
  private Instant createdAt;

  public LikeEntity(Like like) {
    this.id = like.id();
    this.postId = like.postId();
    this.accountId = like.accountId();
    this.createdAt = like.createdAt();
  }

  public Like toDomain() {
    return new Like(id, postId, accountId, createdAt);
  }
}
