package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindPostCommentsUseCase;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindPostCommentsService implements FindPostCommentsUseCase {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final PostValidatorService postValidatorService;

  public FindPostCommentsService(CommentRepository commentRepository, CommentMapper commentMapper,
      PostValidatorService postValidatorService) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
    this.postValidatorService = postValidatorService;
  }

  public List<CommentResponseDTO> handle(String postId) {
    boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);

    if (!isValidPost)
      throw new ResourceNotFoundException("This post cannot be found");

    return this.commentRepository.findByPostId(postId).stream().map(commentMapper::toResponse).toList();
  }
}
