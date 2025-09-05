package com.raponi.blog.application.service.comment;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.comment.CreateCommentUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.dto.CommentResponseDTO;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.CommentMapper;

@Service
public class CreateCommentService implements CreateCommentUseCase {

  private final CommentRepository commentRepository;
  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;
  private final CommentMapper commentMapper;

  public CreateCommentService(CommentRepository commentRepository, AccountRepository accountRepository,
      PostRepository postRepository,
      AccountValidatorService accountValidatorService,
      PostValidatorService postValidatorService, CommentMapper commentMapper) {
    this.commentRepository = commentRepository;
    this.accountRepository = accountRepository;
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
    this.postValidatorService = postValidatorService;
    this.commentMapper = commentMapper;
  }

  @Override
  public CommentResponseDTO handle(String accountId, String postId, CreateCommentRequestDTO requestDTO) {
    this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = this.postRepository.findById(postId).get();

    Optional<Account> author = this.accountRepository.findById(post.accountId());
    boolean validAuthorAccount = this.accountValidatorService.verifyPresenceAndActive(author);

    Optional<Account> account = this.accountRepository.findById(accountId);
    boolean validAccount = this.accountValidatorService.verifyPresenceAndActive(account);
    if (validAuthorAccount) {
      if (validAccount) {
        Comment createdComment = Comment.create(requestDTO.getContent(), accountId, post.id());
        Comment savedComment = this.commentRepository.save(createdComment);
        return this.commentMapper.toResponse(savedComment);
      }
      throw new AccessDeniedException("You can't release this action please active your account.");
    }
    throw new AccessDeniedException("The owner of this post has has their account disabled.");
  }

}
