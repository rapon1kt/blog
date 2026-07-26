package com.raponi.blog.infrastructure.persistence.repository;

import com.raponi.blog.infrastructure.persistence.entity.CommentEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCommentRepository extends MongoRepository<CommentEntity, String> {
  List<CommentEntity> findByPostId(String postId);

  List<CommentEntity> findByAuthorId(String authorId);

  void deleteByAuthorIdAndPostId(String authorId, String postId);

  void deleteByAuthorIdAndTargetAuthorId(String authorId, String targetAuthorId);

  void deleteByAuthorId(String authorId);

  void deleteByPostId(String postId);

  List<CommentEntity> findByCommentIdAndAnswerTrue(String commentId);
}
