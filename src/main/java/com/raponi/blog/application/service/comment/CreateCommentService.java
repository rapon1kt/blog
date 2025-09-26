package com.raponi.blog.application.service.comment;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.notification.CreateNotificationService;
import com.raponi.blog.application.usecase.comment.CreateCommentUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.NotificationType;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class CreateCommentService implements CreateCommentUseCase {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;
  private final CommentMapper commentMapper;
  private final CreateNotificationService createNotificationService;

  public CreateCommentService(CommentRepository commentRepository,
      PostRepository postRepository,
      AccountValidatorService accountValidatorService,
      PostValidatorService postValidatorService, CommentMapper commentMapper,
      CreateNotificationService createNotificationService) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
    this.postValidatorService = postValidatorService;
    this.commentMapper = commentMapper;
    this.createNotificationService = createNotificationService;
  }

  @Override
  public CommentResponseDTO handle(String accountId, String postId, CreateCommentRequestDTO requestDTO) {
    boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);

    if (isValidPost) {
      Post post = this.postRepository.findById(postId).get();
      boolean validAuthorAccount = this.accountValidatorService.verifyPresenceAndActive("_id", post.getAuthorId());
      boolean validAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
      if (validAuthorAccount) {
        if (validAccount) {
          if (this.accountValidatorService.isBlocked(post.getAuthorId()))
            throw new AccessDeniedException("You cannot comment on this account's post.");
          Comment createdComment = Comment.create(accountId, postId, requestDTO.getContent());
          Comment savedComment = this.commentRepository.save(createdComment);
          this.createNotificationService.handle(post.getAuthorId(), accountId, NotificationType.COMMENT, postId);
          return this.commentMapper.toResponse(savedComment);
        }
        throw new AccessDeniedException("You can't release this action please active your account.");
      }
      throw new AccessDeniedException("The owner of this post has has their account disabled.");
    }
    throw new ResourceNotFoundException("This post cannot be found");

  }

}
