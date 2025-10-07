package com.raponi.blog.application.validators;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class BanValidatorService {

  private final BanRepository banRepository;
  private final AccountRepository accountRepository;

  public BanValidatorService(BanRepository banRepository, AccountRepository accountRepository) {
    this.banRepository = banRepository;
    this.accountRepository = accountRepository;
  }

  public boolean isBanValid(String accountId) {
    Optional<Ban> optionalBan = this.banRepository.findTopByBannedIdOrderByBannedAtDesc(accountId);
    if (!optionalBan.isPresent()) {
      accountRepository.findById(accountId).get().setBanned(false);
      return true;
    }
    Ban ban = optionalBan.get();
    switch (ban.getStatus()) {
      case REPLACED:
        return true;
      case DESACTIVE:
        return true;
      case PERMANENTLY_ACTIVE:
        return false;
      case EXPIRED:
      case ACTIVE:
        if (verifyExpiration(ban))
          return true;
        return false;
      default:
        return true;
    }
  }

  private boolean verifyExpiration(Ban ban) {
    if (ban.getExpiresAt().isBefore(Instant.now())) {
      expireBan(ban);
      return true;
    }
    return false;
  }

  private void expireBan(Ban ban) {
    ban.setStatus(BanStatus.EXPIRED);
    this.banRepository.save(ban);
    unbanAccount(ban.getBannedId());
  }

  private void unbanAccount(String bannedId) {
    Account account = this.accountRepository.findById(bannedId)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found."));
    account.setBanned(false);
    this.accountRepository.save(account);
  }

}
