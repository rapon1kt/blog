package com.raponi.blog.application.service.comment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.DeleteCommentUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class DeleteCommentService implements DeleteCommentUseCase {

  private final CommentRepository commentRepository;
  private final CommentValidatorService commentValidatorService;
  private final AccountValidatorService accountValidatorService;

  public DeleteCommentService(CommentRepository commentRepository, CommentValidatorService commentValidatorService,
      AccountValidatorService accountValidatorService) {
    this.commentRepository = commentRepository;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String commentId, String postId) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (isValidComment) {
      boolean isAuthorized = this.accountValidatorService.verifyAuthority("_id", accountId);
      if (isAuthorized) {
        this.commentRepository.deleteById(commentId);
        List<Comment> commentAnswers = this.commentRepository.findByCommentIdAndAnswerTrue(commentId);
        commentAnswers.forEach(comment -> {
          this.commentRepository.deleteById(comment.getId());
        });
        return "Comment deleted with success!";
      }
      throw new AccessDeniedException("You don't have permission to do this.");
    }
    throw new ResourceNotFoundException("This comment cannot be found.");
  }

}
