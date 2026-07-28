package com.raponi.blog.application.service.account;

import com.raponi.blog.application.usecase.account.LoginAccountCommand;
import com.raponi.blog.application.usecase.account.LoginAccountUseCase;
import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.port.TokenGeneratorService;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginAccountService implements LoginAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoder;
  private final TokenGeneratorService tokenGenerator;
  private final BanRepository banRepository;
  private final BanValidatorService banValidatorService;

  public LoginAccountService(
    AccountRepository accountRepository,
    PasswordEncoderService passwordEncoder,
    TokenGeneratorService tokenGenerator,
    BanRepository banRepository,
    BanValidatorService banValidatorService
  ) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenGenerator = tokenGenerator;
    this.banRepository = banRepository;
    this.banValidatorService = banValidatorService;
  }

  @Override
  public String handle(LoginAccountCommand command) {
    Account account = this.accountRepository
      .findByUsername(command.username())
      .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password."));

    if (!this.passwordEncoder.matches(command.password(), account.getPassword())) {
      throw new InvalidCredentialsException("Invalid username or password.");
    }

    validateBanStatus(account);

    return this.tokenGenerator.generateToken(account);
  }

  private void validateBanStatus(Account account) {
    if (!account.isBanned() && !banValidatorService.isBanValid(account.getId())) {
      account.setBanned(true);
      this.accountRepository.save(account);
    }

    if (account.isBanned() && !banValidatorService.isBanValid(account.getId())) {
      Ban activeBan = this.banRepository
        .findTopByBannedIdOrderByBannedAtDesc(account.getId())
        .get();
      String reason = activeBan.getReason() + " - " + activeBan.getModeratorDescription();

      if (activeBan.getStatus().equals(BanStatus.PERMANENTLY_ACTIVE)) {
        throw new AccessDeniedException("Your account is banned permanently. Reason: " + reason);
      }
      throw new AccessDeniedException(
        "Your account is temporarily banned until " +
          activeBan.getExpiresAt() +
          ". Reason: " +
          reason
      );
    }
  }
}
