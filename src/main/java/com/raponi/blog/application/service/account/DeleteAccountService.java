package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.*;
import com.raponi.blog.presentation.dto.DeleteAccountRequestDTO;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  private final LikeRepository likeRepository;
  private final FollowRepository followRepository;
  private final CommentRepository commentRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteAccountService(AccountRepository accountRepository, PostRepository postRepository,
      LikeRepository likeRepository, FollowRepository followRepository, CommentRepository commentRepository,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.postRepository = postRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.commentRepository = commentRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, DeleteAccountRequestDTO request) {
    Account account = this.accountValidatorService.getAccountWithPasswordConfirmation(
        accountId,
        request.getPassword());
    this.accountRepository.deleteById(account.id());
    this.postRepository.deleteByAccountId(account.id());
    this.likeRepository.deleteByAccountId(account.id());
    this.followRepository.deleteByFollowerId(account.id());
    this.followRepository.deleteByFollowingId(account.id());
    this.commentRepository.deleteByAccountId(account.id());
    return "Account with id equals " + account.id() + " deleted with success";
  }

}
