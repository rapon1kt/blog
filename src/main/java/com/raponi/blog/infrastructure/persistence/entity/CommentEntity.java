package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Comment;

import lombok.Data;

@Data
@Document(collection = "comments")
public class CommentEntity {
  @Id
  String id;
  String content;
  String accountId;
  String postId;
  Instant createdAt;
  Instant modifiedAt;

  public CommentEntity(Comment comment) {
    this.id = comment.id();
    this.content = comment.content();
    this.accountId = comment.accountId();
    this.postId = comment.postId();
    this.createdAt = comment.createdAt();
    this.modifiedAt = comment.modifiedAt();
  }

  public Comment toDomain() {
    return new Comment(id, content, accountId, postId, createdAt, modifiedAt);
  }

}
