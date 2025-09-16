package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Comment;

public interface CommentRepository {

  public Comment save(Comment comment);

  public Optional<Comment> findById(String id);

  public List<Comment> findAll();

  public List<Comment> findByCommentIdAndAnswerTrue(String commentId);

  public List<Comment> findByPostId(String postId);

  public List<Comment> findByAccountId(String accountId);

  public void deleteByAccountIdAndPostId(String accountId, String postId);

  public void deleteByAccountId(String accountId);

  public void deleteByPostId(String postId);

  public void deleteById(String id);

}
