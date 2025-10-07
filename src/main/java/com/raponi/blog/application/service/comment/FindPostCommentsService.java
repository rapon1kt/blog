package com.raponi.blog.application.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.FindPostCommentsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class FindPostCommentsService implements FindPostCommentsUseCase {

  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public FindPostCommentsService(CommentRepository commentRepository, CommentMapper commentMapper,
      PostValidatorService postValidatorService, AccountValidatorService accountValidatorService) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  public List<CommentResponseDTO> handle(String postId) {
    boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);

    if (!isValidPost)
      throw new ResourceNotFoundException("This post cannot be found");

    if (this.accountValidatorService.isAdmin()) {
      return this.commentRepository.findByPostId(postId).stream().map(commentMapper::toResponse).toList();
    } else {
      List<Comment> comments = this.commentRepository.findByPostId(postId);
      List<Comment> commentsOfNonBlockedAndNonBanned = new ArrayList<Comment>();
      comments.forEach(comment -> {
        boolean isViwerBlocked = this.accountValidatorService.isBlocked(comment.getAuthorId());
        boolean isAccountBanned = this.accountValidatorService.isBanned(comment.getAuthorId());
        if (!isViwerBlocked && !isAccountBanned)
          commentsOfNonBlockedAndNonBanned.add(comment);
      });
      return commentsOfNonBlockedAndNonBanned.stream().map(commentMapper::toResponse).toList();
    }
  }
}
