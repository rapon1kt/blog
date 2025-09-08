package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Post;

import lombok.Data;

@Data
@Document(collection = "posts")
public class PostEntity {
  @Id
  private String id;
  private String title;
  private String content;
  private boolean privateStatus;
  private String accountId;
  private Instant createdAt;
  private Instant modifiedAt;

  public PostEntity(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.privateStatus = post.isPrivateStatus();
    this.accountId = post.getAccountId();
    this.createdAt = post.getCreatedAt();
    this.modifiedAt = post.getModifiedAt();
  }
}
