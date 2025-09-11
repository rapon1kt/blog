package com.raponi.blog.application.service.follow;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.follow.FollowAndUnfollowAccountUseCase;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.FollowRepository;

import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class FollowAndUnfollowAccountService implements FollowAndUnfollowAccountUseCase {

  private final FollowRepository followRepository;
  private final AccountRepository accountRepository;

  public FollowAndUnfollowAccountService(FollowRepository followRepository, AccountRepository accountRepository) {
    this.followRepository = followRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public String handle(String followerId, String followingId) {
    if (followerId.equals(followingId))
      throw new BusinessRuleException("You can't follow yourself.");

    Account originAcc = this.accountRepository.findById(followerId)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found."));

    Account destinyAcc = this.accountRepository.findById(followingId)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found."));

    if (!originAcc.isActive())
      throw new AccessDeniedException("You must active your account to do this.");

    if (destinyAcc.isActive()) {
      Boolean exists = this.followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
      if (exists) {
        this.followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        return ("Unfollowed " + destinyAcc.getUsername());
      }
      Follow follow = Follow.create(followerId, followingId);
      this.followRepository.save(follow);
      return ("Followed " + destinyAcc.getUsername());
    }
    throw new AccessDeniedException("This account is desactivated.");
  }

}
