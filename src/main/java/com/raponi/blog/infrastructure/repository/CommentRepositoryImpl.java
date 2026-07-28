package com.raponi.blog.infrastructure.repository;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.infrastructure.persistence.entity.CommentEntity;
import com.raponi.blog.infrastructure.persistence.mapper.CommentInfraMapper;
import com.raponi.blog.infrastructure.persistence.repository.MongoCommentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CommentRepositoryImpl implements CommentRepository {

  private final MongoCommentRepository mongoRepository;
  private final CommentInfraMapper commentMapper;

  public CommentRepositoryImpl(MongoCommentRepository mongoRepository, CommentInfraMapper commentMapper) {
    this.mongoRepository = mongoRepository;
    this.commentMapper = commentMapper;
  }

  @Override
  public Comment save(Comment comment) {
    CommentEntity commentEntity = this.commentMapper.toEntity(comment);
    CommentEntity savedCommentEntity = this.mongoRepository.save(commentEntity);
    return this.commentMapper.toDomain(savedCommentEntity);
  }

  @Override
  public Optional<Comment> findById(String id) {
    Optional<CommentEntity> commentEntity = this.mongoRepository.findById(id);
    return commentEntity.map(commentMapper::toDomain);
  }

  @Override
  public List<Comment> findByCommentIdAndAnswerTrue(String commentId) {
    return this.mongoRepository.findByCommentIdAndAnswerTrue(commentId).stream().map(commentMapper::toDomain).toList();
  }

  @Override
  public List<Comment> findByAuthorId(String authorId) {
    return this.mongoRepository.findByAuthorId(authorId).stream().map(commentMapper::toDomain).toList();
  }

  @Override
  public List<Comment> findByPostId(String postId) {
    return this.mongoRepository.findByPostId(postId).stream().map(commentMapper::toDomain).toList();
  }

  @Override
  public void deleteByAuthorIdAndPostId(String authorId, String postId) {
    this.mongoRepository.deleteByAuthorIdAndPostId(authorId, postId);
  }

  @Override
  public void deleteByAuthorIdAndTargetAuthorId(String authorId, String targetAuthorId) {
    this.mongoRepository.deleteByAuthorIdAndTargetAuthorId(authorId, targetAuthorId);
  }

  @Override
  public void deleteByPostId(String postId) {
    this.mongoRepository.deleteByPostId(postId);
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public void deleteByAuthorId(String authorId) {
    this.mongoRepository.deleteByAuthorId(authorId);
  }
}
