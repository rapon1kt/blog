package com.raponi.blog.application.service.ban;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.ban.BanAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.dto.BanAccountRequestDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class BanAccountService implements BanAccountUseCase {

  private final BanRepository banRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public BanAccountService(BanRepository banRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.banRepository = banRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Ban handle(String moderatorId, String bannedId, BanReason reason, BanAccountRequestDTO requestDTO) {
    boolean validAccount = this.accountValidatorService.verifyAccountWithAccountId(bannedId);
    if (!validAccount)
      throw new ResourceNotFoundException("This account cannot be found");

    long countOfBans = this.banRepository.countByBannedId(bannedId);

    if (countOfBans == 4) {
      Instant expiresAt = Instant.now().plus(Duration.ofDays(3650000));
      Ban maxBan = new Ban(reason.getCategory(), reason, requestDTO.getDescription(), moderatorId, bannedId, expiresAt);
      maxBan.setStatus(BanStatus.PERMANENTLY_ACTIVE);
      return this.banRepository.save(maxBan);
    }

    this.banRepository.findTopByBannedIdAndOrderByBannedAt(bannedId).ifPresent(existingBan -> {
      existingBan.setStatus(BanStatus.REPLACED);
      this.banRepository.save(existingBan);
    });

    Account bannedAccount = this.accountRepository.findById(bannedId).get();
    bannedAccount.setBanned(true);
    this.accountRepository.save(bannedAccount);

    Instant expiresAt = Instant.now().plus(Duration.ofDays(requestDTO.getTime()));
    Ban newBan = new Ban(reason.getCategory(), reason, requestDTO.getDescription(), moderatorId, bannedId, expiresAt);
    return this.banRepository.save(newBan);
  }

}
