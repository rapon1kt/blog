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
    this.id = post.id();
    this.title = post.title();
    this.content = post.content();
    this.privateStatus = post.privateStatus();
    this.accountId = post.accountId();
    this.createdAt = post.createdAt();
    this.modifiedAt = post.modifiedAt();
  }

  public Post toDomain() {
    return new Post(id, title, content, privateStatus, accountId, createdAt, modifiedAt);
  }

}
