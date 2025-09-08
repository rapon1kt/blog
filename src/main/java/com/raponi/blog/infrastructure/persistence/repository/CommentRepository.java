package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.raponi.blog.domain.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

  List<Comment> existsByPostId(String postId);

  List<Comment> existsByAccountId(String accountId);

  void deleteByAccountId(String accountId);

  void deleteByPostId(String postId);

  List<Comment> findByCommentIdAndIsAnswerTrue(String commentId);

}
