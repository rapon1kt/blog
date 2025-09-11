package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "follows")
public class FollowEntity {
  @Id
  String id;
  String followerId;
  String followingId;
  Instant createdAt;

}
