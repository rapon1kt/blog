package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.usecase.follow.FindAccountFollowingUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.FollowRepository;

@Service
public class FindAccountFollowingService implements FindAccountFollowingUseCase {

  private final FollowRepository followRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountFollowingService(FollowRepository followRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.followRepository = followRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<String> handle(String accountId) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(account);
    if (verifiedAccount)
      return this.followRepository.findByFollowerId(accountId).stream().map(Follow::followingId).toList();
    else
      throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

}
