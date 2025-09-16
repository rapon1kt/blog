package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.CommentEntity;

@Repository
public interface MongoCommentRepository extends MongoRepository<CommentEntity, String> {

  List<CommentEntity> findByPostId(String postId);

  List<CommentEntity> findByAccountId(String accountId);

  void deleteByAccountIdAndPostId(String accountId, String postId);

  void deleteByAccountId(String accountId);

  void deleteByPostId(String postId);

  List<CommentEntity> findByCommentIdAndAnswerTrue(String commentId);

}
