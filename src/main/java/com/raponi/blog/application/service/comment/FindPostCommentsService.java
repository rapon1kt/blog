package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindPostCommentsUseCase;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindPostCommentsService implements FindPostCommentsUseCase {
  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  public FindPostCommentsService(CommentRepository commentRepository, CommentMapper commentMapper) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
  }

  public List<CommentResponseDTO> handle(String postId) {
    return this.commentRepository.existsByPostId(postId).stream().map(commentMapper::toResponse)
        .toList();
  }
}
