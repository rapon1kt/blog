package com.raponi.blog.application.service.comment;

import com.raponi.blog.application.service.notification.CreateNotificationService;
import com.raponi.blog.application.usecase.comment.CreateCommentCommand;
import com.raponi.blog.application.usecase.comment.CreateCommentUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.PostNotFoundException;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.NotificationType;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.domain.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentService implements CreateCommentUseCase {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;
  private final CreateNotificationService createNotificationService;

  public CreateCommentService(
      CommentRepository commentRepository,
      PostRepository postRepository,
      AccountValidatorService accountValidatorService,
      PostValidatorService postValidatorService,
      CreateNotificationService createNotificationService) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
    this.createNotificationService = createNotificationService;
  }

  @Override
  public Comment handle(String accountId, String postId, CreateCommentCommand command) {
    boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);

    if (isValidPost) {
      Post post = this.postRepository.findById(postId).get();
      boolean validAuthorAccount = this.accountValidatorService.verifyPresenceAndActive("_id", post.getAuthorId());
      boolean validAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
      if (validAuthorAccount) {
        if (validAccount) {
          if (this.accountValidatorService.isBlocked(post.getAuthorId()))
            throw new AccessDeniedException(
                "You cannot comment on this account's post.");
          Comment createdComment = Comment.create(accountId, postId, post.getAuthorId(), command.content());
          Comment savedComment = this.commentRepository.save(createdComment);
          this.createNotificationService.handle(post.getAuthorId(), accountId, NotificationType.COMMENT, postId);
          return savedComment;
        }
        throw new AccessDeniedException("You cannot release this action please active your account.");
      }
      throw new AccessDeniedException("The author of this post had their account disabled.");
    }
    throw new PostNotFoundException("This post cannot be found");
  }
}
