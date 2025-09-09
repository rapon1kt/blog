package com.raponi.blog.application.validators;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.CommentValidatorUseCase;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;

@Service
public class CommentValidatorService implements CommentValidatorUseCase {

  private final CommentRepository commentRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;

  public CommentValidatorService(CommentRepository commentRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService,
      PostValidatorService postValidatorService) {
    this.commentRepository = commentRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.postValidatorService = postValidatorService;
  }

  @Override
  public boolean isValidComment(String commentId) {
    Optional<Comment> comment = this.commentRepository.findById(commentId);
    if (comment.isPresent()) {
      if (isValidPost(commentId)) {
        return (isValidAccount(commentId));
      }
      return false;
    }
    return false;
  }

  private boolean isValidAccount(String commentId) {
    Comment comment = this.commentRepository.findById(commentId).get();
    Optional<Account> acc = this.accountRepository.findById(comment.getAccountId());
    return this.accountValidatorService.verifyPresenceAndActive(acc);
  }

  private boolean isValidPost(String commentId) {
    Comment comment = this.commentRepository.findById(commentId).get();
    return this.postValidatorService.validatePostPresenceAndPrivate(comment.getPostId());
  }

}
