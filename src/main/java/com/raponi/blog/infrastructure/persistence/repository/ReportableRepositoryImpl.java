package com.raponi.blog.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.model.Reportable;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.domain.repository.ReportableRepository;

@Repository
public class ReportableRepositoryImpl implements ReportableRepository {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  public ReportableRepositoryImpl(PostRepository postRepository, CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  @Override
  public Optional<Reportable> findById(String targetId, ReportTargetType reportTargetType) {
    return switch (reportTargetType) {
      case POST -> this.postRepository.findById(targetId).map(p -> (Reportable) p);
      case COMMENT -> this.commentRepository.findById(targetId).map(c -> (Reportable) c);
    };
  }

}
