package com.raponi.blog.application.service.follow;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.follow.FollowAndUnfollowAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.FollowRepository;

import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;

@Service
public class FollowAndUnfollowAccountService implements FollowAndUnfollowAccountUseCase {

  private final FollowRepository followRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public FollowAndUnfollowAccountService(FollowRepository followRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.followRepository = followRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String followerId, String followingId) {
    if (followerId.equals(followingId))
      throw new BusinessRuleException("You can't follow yourself.");

    boolean isFollowerValid = this.accountValidatorService.verifyAccountWithAccountId(followerId);
    boolean isFollowingValid = this.accountValidatorService.verifyPresenceAndActive("_id", followingId);
    if (!isFollowerValid || !isFollowingValid)
      throw new AccessDeniedException("You don't have permission to do this.");

    boolean isViewerBlocked = this.accountValidatorService.isBlocked(followingId);
    if (isViewerBlocked)
      throw new AccessDeniedException("You cannot follow this account.");
    Boolean exists = this.followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    Account destinyAcc = this.accountRepository.findById(followingId).get();
    if (exists) {
      this.followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
      return ("Unfollowed " + destinyAcc.getUsername());
    }
    Follow follow = Follow.create(followerId, followingId);
    this.followRepository.save(follow);
    return ("Followed " + destinyAcc.getUsername());
  }

}
