package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.usecase.comment.FindPostCommentsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindPostCommentsService implements FindPostCommentsUseCase {
  private final CommentRepository commentRepository;

  public FindPostCommentsService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public List<Http.CommentResponseBody> handle(String postId) {
    return this.commentRepository.existsByPostId(postId).stream().map(Comment::toResponseBody)
        .toList();
  }
}
