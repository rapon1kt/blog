package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.PostVisibility;

import lombok.Data;

@Data
@Document(collection = "posts")
public class PostEntity {
  @Id
  private String id;
  private String title;
  private String content;
  private PostVisibility postVisibility;
  private long likeCount;
  private String authorId;
  private boolean pinned;
  private Instant createdAt;
  private Instant modifiedAt;

}
