package com.raponi.blog.application.service.follow;

import java.util.Optional;

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
    Optional<Account> originAcc = this.accountRepository.findById(followerId);
    Optional<Account> destinyAcc = this.accountRepository.findById(followingId);

    if (originAcc.isPresent() && destinyAcc.isPresent()) {
      if (destinyAcc.get().active()) {
        if (this.followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
          this.followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
          return ("Unfollowed " + destinyAcc.get().username());
        }
        Follow follow = Follow.create(followerId, followingId);
        this.followRepository.save(follow);
        return ("Followed " + destinyAcc.get().username());
      }
      throw new IllegalArgumentException("A conta que você está tentando seguir está desativada.");
    }
    throw new IllegalArgumentException("A usuário em questão não existe.");
  }

}
