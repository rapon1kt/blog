package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.usecase.follow.FindAccountFollowersUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.FollowRepository;

@Service
public class FindAccountFollowersService implements FindAccountFollowersUseCase {

  private final FollowRepository followRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountFollowersService(FollowRepository followRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.followRepository = followRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<String> handle(String accountId) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(acc);
    if (verifiedAccount)
      return this.followRepository.findByFollowingId(accountId).stream().map(Follow::followerId).toList();
    else
      throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

}
