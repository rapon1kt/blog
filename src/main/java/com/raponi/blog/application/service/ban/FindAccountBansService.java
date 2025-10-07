package com.raponi.blog.application.service.ban;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.ban.FindAccountBansUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class FindAccountBansService implements FindAccountBansUseCase {

  private final BanRepository banRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountBansService(BanRepository banRepository, AccountValidatorService accountValidatorService) {
    this.banRepository = banRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Ban> handle(String bannedId) {
    boolean isVerified = this.accountValidatorService.verifyAccountWithAccountId(bannedId);
    if (isVerified)
      return this.banRepository.findAllByBannedIdOrderByBannedAtDesc(bannedId);
    throw new ResourceNotFoundException("This account cannot be found.");
  }

}
