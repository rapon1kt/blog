package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    Optional<Account> acc = this.accountRepository.findById(accountId);
    this.accountValidatorService.verifyPresenceAndActive(acc, role);
    return this.followRepository.findByFollowingId(accountId).stream().map(Follow::followerId).toList();
  }

}
