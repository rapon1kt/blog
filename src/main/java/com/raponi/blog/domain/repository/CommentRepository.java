package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Comment;

public interface CommentRepository {

  public Comment save(Comment comment);

  public Optional<Comment> findById(String id);

  public List<Comment> findAll();

  public List<Comment> findByCommentIdAndIsAnswerTrue(String commentId);

  public List<Comment> existsByPostId(String postId);

  public List<Comment> existsByAccountId(String accountId);

  public void deleteByAccountId(String accountId);

  public void deleteByPostId(String postId);

  public void deleteById(String id);

}
