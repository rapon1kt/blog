package com.raponi.blog.application.service.follow;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.usecase.follow.FollowAndUnfollowAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.FollowRepository;

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
      throw new IllegalArgumentException("Não é possível realizar essa ação.");

    Account originAcc = this.accountRepository.findById(followerId)
        .orElseThrow(() -> new IllegalArgumentException("A conta não pode ser encontrada"));

    Account destinyAcc = this.accountRepository.findById(followingId)
        .orElseThrow(() -> new IllegalArgumentException("A conta não pode ser encontrada"));

    if (!originAcc.active())
      throw new AccessDeniedException("Você deve reativer sua conta para realizar essa ação.");

    if (destinyAcc.active()) {
      Boolean exists = this.followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
      if (exists) {
        this.followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
        return ("Unfollowed " + destinyAcc.username());
      }
      Follow follow = Follow.create(followerId, followingId);
      this.followRepository.save(follow);
      return ("Followed " + destinyAcc.username());
    }
    throw new IllegalArgumentException("Esse usuário está com a conta desativada.");
  }

}
