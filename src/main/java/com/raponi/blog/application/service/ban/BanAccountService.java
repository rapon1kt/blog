package com.raponi.blog.application.service.ban;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.ban.BanAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.dto.BanAccountRequestDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class BanAccountService implements BanAccountUseCase {

  private final BanRepository banRepository;
  private final AccountValidatorService accountValidatorService;

  public BanAccountService(BanRepository banRepository, AccountValidatorService accountValidatorService) {
    this.banRepository = banRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Ban handle(String moderatorId, String bannedId, BanReason reason, BanAccountRequestDTO requestDTO) {
    boolean validAccount = this.accountValidatorService.verifyAccountWithAccountId(bannedId);
    if (!validAccount)
      throw new ResourceNotFoundException("This account cannot be found");

    this.banRepository.findByBannedIdAndActiveTrue(bannedId).ifPresent(existingBan -> {
      existingBan.setActive(false);
      this.banRepository.save(existingBan);
    });

    Instant expiresAt = Instant.now().plus(Duration.ofDays(requestDTO.getTime()));
    Ban newBan = new Ban(reason.getCategory(), reason, requestDTO.getDescription(), moderatorId, bannedId, expiresAt);
    return this.banRepository.save(newBan);
  }

}
