package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindAllCommentAnswersUseCase;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindAllCommentAnswersService implements FindAllCommentAnswersUseCase {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final CommentValidatorService commentValidatorService;

  public FindAllCommentAnswersService(CommentRepository commentRepository, CommentMapper commentMapper,
      CommentValidatorService commentValidatorService) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
    this.commentValidatorService = commentValidatorService;
  }

  @Override
  public List<CommentResponseDTO> handle(String commentId) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (!isValidComment)
      throw new ResourceNotFoundException("This comment cannot be found");

    return this.commentRepository.findByCommentIdAndAnswerTrue(commentId).stream().map(commentMapper::toResponse)
        .toList();
  }

}
