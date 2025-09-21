package com.raponi.blog.application.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindAllCommentAnswersUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindAllCommentAnswersService implements FindAllCommentAnswersUseCase {

  private final AccountValidatorService accountValidatorService;

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final CommentValidatorService commentValidatorService;

  public FindAllCommentAnswersService(CommentRepository commentRepository, CommentMapper commentMapper,
      CommentValidatorService commentValidatorService, AccountValidatorService accountValidatorService) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<CommentResponseDTO> handle(String commentId) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (!isValidComment)
      throw new ResourceNotFoundException("This comment cannot be found");

    if (this.accountValidatorService.isAdmin()) {
      return this.commentRepository.findByCommentIdAndAnswerTrue(commentId).stream().map(commentMapper::toResponse)
          .toList();
    } else {
      List<Comment> answers = this.commentRepository.findByCommentIdAndAnswerTrue(commentId);
      List<Comment> answersOfNonBlocked = new ArrayList<Comment>();
      answers.forEach(comment -> {
        if (!this.accountValidatorService.isBlocked(comment.getAuthorId()))
          answersOfNonBlocked.add(comment);
      });
      return answersOfNonBlocked.stream().map(commentMapper::toResponse).toList();
    }
  }

}
