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
    this.id = comment.getId();
    this.content = comment.getContent();
    this.accountId = comment.getAccountId();
    this.postId = comment.getPostId();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
  }

}
