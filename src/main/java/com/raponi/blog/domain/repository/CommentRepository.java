package com.raponi.blog.domain.repository;

import com.raponi.blog.domain.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  public Comment save(Comment comment);

  public Optional<Comment> findById(String id);

  public List<Comment> findByCommentIdAndAnswerTrue(String commentId);

  public List<Comment> findByPostId(String postId);

  public List<Comment> findByAuthorId(String authorId);

  public void deleteByAuthorIdAndPostId(String authorId, String postId);

  public void deleteByAuthorIdAndTargetAuthorId(String authorId, String targetAuthorId);

  public void deleteByAuthorId(String authorId);

  public void deleteByPostId(String postId);

  public void deleteById(String id);
}
