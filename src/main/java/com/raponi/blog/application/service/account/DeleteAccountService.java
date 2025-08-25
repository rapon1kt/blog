package com.raponi.blog.application.service.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.FollowRepository;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  private final LikeRepository likeRepository;
  private final FollowRepository followRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteAccountService(AccountRepository accountRepository, PostRepository postRepository,
      LikeRepository likeRepository, FollowRepository followRepository,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.postRepository = postRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String password) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Account account = this.accountValidatorService.getAccountWithPasswordConfirmation(
        auth.getName(),
        accountId,
        password,
        auth.getAuthorities().iterator().next().getAuthority());
    this.accountRepository.deleteById(account.id());
    this.postRepository.deleteByAccountId(account.id());
    this.likeRepository.deleteByAccountId(account.id());
    this.followRepository.deleteByFollowerId(account.id());
    this.followRepository.deleteByFollowingId(account.id());
    return "Account with id equals " + account.id() + " deleted with success";
  }

}
