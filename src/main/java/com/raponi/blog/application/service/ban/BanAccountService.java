package com.raponi.blog.application.service.ban;

import com.raponi.blog.application.usecase.ban.BanAccountCommand;
import com.raponi.blog.application.usecase.ban.BanAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccountNotFoundException;
import com.raponi.blog.domain.exception.BusinessRuleException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import java.time.Duration;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class BanAccountService implements BanAccountUseCase {

  private final BanRepository banRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public BanAccountService(BanRepository banRepository, AccountRepository accountRepository, AccountValidatorService accountValidatorService) {
    this.banRepository = banRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Ban handle(String moderatorId, String bannedId, BanReason reason, BanAccountCommand command) {
    boolean validAccount = this.accountValidatorService.verifyAccountWithAccountId(bannedId);
    if (!validAccount) throw new AccountNotFoundException("This account cannot be found");

    long countOfBans = this.banRepository.countByBannedId(bannedId);

    if (countOfBans == 4) {
      Instant expiresAt = Instant.now().plus(Duration.ofDays(3650000));
      Ban maxBan = new Ban(reason.getCategory(), reason, command.description(), moderatorId, bannedId, expiresAt);
      maxBan.setStatus(BanStatus.PERMANENTLY_ACTIVE);
      this.banRepository.findTopByBannedIdOrderByBannedAtDesc(bannedId).ifPresent(existingBan -> {
        if (existingBan.getStatus().equals(BanStatus.ACTIVE)) {
          existingBan.setStatus(BanStatus.REPLACED);
          this.banRepository.save(existingBan);
        }
      });
      return this.banRepository.save(maxBan);
    }

    if (countOfBans >= 5) {
      throw new BusinessRuleException("This account is already banned permanently.");
    }

    this.banRepository.findTopByBannedIdOrderByBannedAtDesc(bannedId).ifPresent(existingBan -> {
      if (existingBan.getStatus().equals(BanStatus.ACTIVE)) {
        existingBan.setStatus(BanStatus.REPLACED);
        this.banRepository.save(existingBan);
      }
    });

    Account bannedAccount = this.accountRepository.findById(bannedId).get();
    bannedAccount.setBanned(true);
    this.accountRepository.save(bannedAccount);

    Instant expiresAt = Instant.now().plus(Duration.ofDays(command.time()));
    Ban newBan = new Ban(reason.getCategory(), reason, command.description(), moderatorId, bannedId, expiresAt);
    return this.banRepository.save(newBan);
  }
}
