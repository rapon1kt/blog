package com.raponi.blog.application.service.comment;

import com.raponi.blog.application.service.notification.CreateNotificationService;
import com.raponi.blog.application.usecase.comment.AnswerCommentUseCase;
import com.raponi.blog.application.usecase.comment.CreateCommentCommand;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.CommentNotFoundException;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.NotificationType;
import com.raponi.blog.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerCommentService implements AnswerCommentUseCase {

  private final CommentRepository commentRepository;
  private final AccountValidatorService accountValidatorService;
  private final CommentValidatorService commentValidatorService;
  private final CreateNotificationService createNotificationService;

  public AnswerCommentService(
      CommentRepository commentRepository,
      CommentValidatorService commentValidatorService,
      AccountValidatorService accountValidatorService,
      CreateNotificationService createNotificationService) {
    this.commentRepository = commentRepository;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
    this.createNotificationService = createNotificationService;
  }

  @Override
  public Comment handle(String accountId, String commentId, CreateCommentCommand command) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (!isValidComment)
      throw new CommentNotFoundException("This comment cannot be found.");

    Comment comment = this.commentRepository.findById(commentId).get();

    if (this.accountValidatorService.isBlocked(comment.getAuthorId()))
      throw new AccessDeniedException("You cannot answer this comment.");

    Comment commentAnswer = Comment.create(accountId, comment.getAuthorId(), comment.getPostId(), command.content());
    commentAnswer.setCommentId(commentId);
    commentAnswer.setAnswer(true);
    Comment savedCommentAnswer = this.commentRepository.save(commentAnswer);
    this.createNotificationService.handle(comment.getAuthorId(), accountId, NotificationType.COMMENT, comment.getId());
    return savedCommentAnswer;
  }
}
