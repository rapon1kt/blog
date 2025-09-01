package com.raponi.blog.application.service.comment;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.comment.CreateCommentUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class CreateCommentService implements CreateCommentUseCase {

  private final CommentRepository commentRepository;
  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;

  public CreateCommentService(CommentRepository commentRepository, AccountRepository accountRepository,
      PostRepository postRepository,
      AccountValidatorService accountValidatorService,
      PostValidatorService postValidatorService) {
    this.commentRepository = commentRepository;
    this.accountRepository = accountRepository;
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
    this.postValidatorService = postValidatorService;
  }

  @Override
  public Http.CommentResponseBody handle(String accountId, String postId, Comment comment) {
    this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = this.postRepository.findById(postId).get();

    Optional<Account> author = this.accountRepository.findById(post.accountId());
    boolean validAuthorAccount = this.accountValidatorService.verifyPresenceAndActive(author);

    Optional<Account> account = this.accountRepository.findById(accountId);
    boolean validAccount = this.accountValidatorService.verifyPresenceAndActive(account);
    if (validAuthorAccount) {
      if (validAccount) {
        Comment cmmnt = Comment.create(comment.content(), accountId, post.id());
        return this.commentRepository.save(cmmnt).toResponseBody();
      }
      throw new AccessDeniedException("Você não pode fazer isso, reative sua conta!");
    }
    throw new IllegalArgumentException("Parece que esse post pertence a uma conta desativada.");
  }

}
