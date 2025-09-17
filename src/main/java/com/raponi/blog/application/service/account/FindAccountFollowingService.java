package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.follow.FindAccountFollowingUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.FollowRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

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
  public List<String> handle(String username) {
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive("username", username);
    if (!verifiedAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findByUsername(username).get();
    return this.followRepository.findByFollowerId(account.getId()).stream().map(Follow::getFollowingId).toList();
  }

}
