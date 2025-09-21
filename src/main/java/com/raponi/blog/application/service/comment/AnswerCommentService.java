package com.raponi.blog.application.service.comment;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.AnswerCommentUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class AnswerCommentService implements AnswerCommentUseCase {

  private final AccountValidatorService accountValidatorService;

  private final CommentMapper commentMapper;
  private final CommentRepository commentRepository;
  private final CommentValidatorService commentValidatorService;

  public AnswerCommentService(CommentMapper commentMapper, CommentRepository commentRepository,
      CommentValidatorService commentValidatorService, AccountValidatorService accountValidatorService) {
    this.commentMapper = commentMapper;
    this.commentRepository = commentRepository;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public CommentResponseDTO handle(String accountId, String commentId, CreateCommentRequestDTO requestDTO) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (!isValidComment)
      throw new ResourceNotFoundException("This comment cannot be found.");

    Comment comment = this.commentRepository.findById(commentId).get();

    if (this.accountValidatorService.isBlocked(comment.getAuthorId()))
      throw new AccessDeniedException("You cannot answer this comment.");

    Comment commentAnswer = Comment.create(accountId, comment.getPostId(), requestDTO.getContent());
    commentAnswer.setCommentId(commentId);
    commentAnswer.setAnswer(true);
    Comment savedCommentAnswer = this.commentRepository.save(commentAnswer);
    CommentResponseDTO commentAnswerResponse = this.commentMapper.toResponse(savedCommentAnswer);
    return commentAnswerResponse;
  }

}
