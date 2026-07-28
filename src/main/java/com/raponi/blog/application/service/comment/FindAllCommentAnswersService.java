package com.raponi.blog.application.service.comment;

import com.raponi.blog.application.usecase.comment.FindAllCommentAnswersUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.exception.CommentNotFoundException;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FindAllCommentAnswersService implements FindAllCommentAnswersUseCase {

  private final CommentRepository commentRepository;
  private final AccountValidatorService accountValidatorService;
  private final CommentValidatorService commentValidatorService;

  public FindAllCommentAnswersService(
      CommentRepository commentRepository,
      CommentValidatorService commentValidatorService,
      AccountValidatorService accountValidatorService) {
    this.commentRepository = commentRepository;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Comment> handle(String commentId) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (!isValidComment)
      throw new CommentNotFoundException("This comment cannot be found");

    if (this.accountValidatorService.isAdmin()) {
      return this.commentRepository.findByCommentIdAndAnswerTrue(commentId);
    } else {
      List<Comment> answers = this.commentRepository.findByCommentIdAndAnswerTrue(commentId);
      List<Comment> answersOfNonBlockedAndNonBanned = new ArrayList<Comment>();
      answers.forEach(comment -> {
        boolean isViwerBlocked = this.accountValidatorService.isBlocked(comment.getAuthorId());
        boolean isAccountBanned = this.accountValidatorService.isBanned(comment.getAuthorId());
        if (!isViwerBlocked && !isAccountBanned)
          answersOfNonBlockedAndNonBanned.add(comment);
      });
      return answersOfNonBlockedAndNonBanned;
    }
  }
}
