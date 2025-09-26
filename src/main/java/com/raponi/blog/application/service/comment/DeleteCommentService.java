package com.raponi.blog.application.service.comment;

import java.util.List;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.comment.DeleteCommentUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.domain.repository.LikeRepository;
import com.raponi.blog.domain.repository.NotificationRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class DeleteCommentService implements DeleteCommentUseCase {

  private final CommentRepository commentRepository;
  private final LikeRepository likeRepository;
  private final NotificationRepository notificationRepository;
  private final CommentValidatorService commentValidatorService;
  private final AccountValidatorService accountValidatorService;

  public DeleteCommentService(CommentRepository commentRepository,
      LikeRepository likeRepository,
      NotificationRepository notificationRepository,
      CommentValidatorService commentValidatorService,
      AccountValidatorService accountValidatorService) {
    this.commentRepository = commentRepository;
    this.likeRepository = likeRepository;
    this.notificationRepository = notificationRepository;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String commentId) {
    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (isValidComment) {
      boolean isAuthorized = this.accountValidatorService.verifyAuthority("_id", accountId);
      if (isAuthorized) {
        Comment comment = this.commentRepository.findById(commentId).get();
        deleteInteractions(commentId, comment.getPostId());
        return "Comment deleted with success!";
      }
      throw new AccessDeniedException("You don't have permission to do this.");
    }
    throw new ResourceNotFoundException("This comment cannot be found.");
  }

  private void deleteInteractions(String commentId, String targetId) {
    List<Comment> commentAnswers = this.commentRepository.findByCommentIdAndAnswerTrue(commentId);
    commentAnswers.forEach(cmmnt -> {
      this.commentRepository.deleteById(cmmnt.getId());
      this.likeRepository.deleteByTargetId(cmmnt.getId());
      this.notificationRepository.deleteByTargetId(targetId);
    });
    this.commentRepository.deleteById(commentId);
    this.likeRepository.deleteByTargetId(commentId);
    this.notificationRepository.deleteByTargetId(targetId);
  }

}
