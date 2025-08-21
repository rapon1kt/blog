package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Follow;

import lombok.Data;

@Data
@Document(collection = "follows")
public class FollowEntity {
  @Id
  String id;
  String followerId;
  String followingId;
  Instant createdAt;

  public FollowEntity(Follow follow) {
    this.id = follow.id();
    this.followerId = follow.followerId();
    this.followingId = follow.followingId();
    this.createdAt = follow.createdAt();
  }

  public Follow toDomain() {
    return new Follow(id, followerId, followingId, createdAt);
  }

}
