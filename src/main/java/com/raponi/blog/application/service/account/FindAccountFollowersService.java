package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.follow.FindAccountFollowersUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Follow;
import com.raponi.blog.domain.repository.FollowRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

@Service
public class FindAccountFollowersService implements FindAccountFollowersUseCase {

  private final FollowRepository followRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountFollowersService(FollowRepository followRepository,
      AccountValidatorService accountValidatorService) {
    this.followRepository = followRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<String> handle(String username) {
    String accountId = this.accountValidatorService.verifyAccountWithUsernameAndReturnId(username);
    if (accountId.equals(null))
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean isViewerBlocked = this.accountValidatorService.isBlocked(accountId);
    boolean isAccountBanned = this.accountValidatorService.isBanned(accountId);
    if (!isViewerBlocked && !isAccountBanned)
      return this.followRepository.findByFollowingId(accountId).stream().map(Follow::getFollowerId)
          .toList();
    return null;
  }

}
