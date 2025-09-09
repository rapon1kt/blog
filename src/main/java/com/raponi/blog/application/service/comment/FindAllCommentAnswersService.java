package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindAllCommentAnswersUseCase;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindAllCommentAnswersService implements FindAllCommentAnswersUseCase {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  public FindAllCommentAnswersService(CommentRepository commentRepository, CommentMapper commentMapper) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
  }

  @Override
  public List<CommentResponseDTO> handle(String commentId) {
    return this.commentRepository.findByCommentIdAndIsAnswerTrue(commentId).stream().map(commentMapper::toResponse)
        .toList();
  }

}
