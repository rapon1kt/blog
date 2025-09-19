package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "comments")
public class CommentEntity {
  @Id
  private String id;
  private String content;
  private String authorId;
  private String postId;
  private String commentId;
  private boolean answer;
  private long likeCount;
  private Instant createdAt;
  private Instant modifiedAt;

}
