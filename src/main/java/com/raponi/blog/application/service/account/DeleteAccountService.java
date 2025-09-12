package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.*;
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
    this.accountRepository.deleteById(account.getId());
    this.postRepository.deleteByAccountId(account.getId());
    this.likeRepository.deleteByAccountId(account.getId());
    this.followRepository.deleteByFollowerId(account.getId());
    this.followRepository.deleteByFollowingId(account.getId());
    this.commentRepository.deleteByAccountId(account.getId());
    return "Account with id equals " + account.getId() + " deleted with success";
  }

}
