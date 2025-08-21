package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.usecase.follow.FindAccountFollowersUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.FollowRepository;

@Service
public class FindAccountFollowersService implements FindAccountFollowersUseCase {

  private final FollowRepository followRepository;
  private final AccountRepository accountRepository;

  public FindAccountFollowersService(FollowRepository followRepository, AccountRepository accountRepository) {
    this.followRepository = followRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public List<String> handle(String accountId) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    if (account.isPresent()) {
      if (account.get().active()) {
        return this.followRepository.findByFollowerId(accountId).stream().map(Follow::followerId).toList();
      }
      throw new IllegalArgumentException("Essa conta está desativada.");
    }
    throw new IllegalArgumentException("O usuário em questão não existe.");
  }

}
