package com.raponi.blog.application.service.ban;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.ban.UnbanAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class UnbanAccountService implements UnbanAccountUseCase {

  private final BanRepository banRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public UnbanAccountService(BanRepository banRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.banRepository = banRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String moderatorId, String bannedId) {

    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(bannedId);
    if (!isValidAccount)
      throw new ResourceNotFoundException("This account cannot be found.");

    Optional<Ban> optionalBan = this.banRepository.findTopByBannedIdOrderByBannedAt(bannedId);
    Account bannedAccount = this.accountRepository.findById(bannedId).get();
    if (optionalBan.isPresent()) {
      Ban existingBan = optionalBan.get();
      existingBan.setStatus(BanStatus.ACTIVE);
      bannedAccount.setBanned(false);
      this.banRepository.save(existingBan);
      this.accountRepository.save(bannedAccount);
      return "Account " + bannedAccount.getUsername() + " unbanned with success!";
    }
    return "The account " + bannedAccount.getUsername() + " don't have an active ban.";
  }

}
