package com.raponi.blog.application.service.ban;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.ban.FindBansUseCase;
import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.BanRepository;

@Service
public class FindBansService implements FindBansUseCase {

  private final BanRepository banRepository;
  private final BanValidatorService banValidatorService;

  public FindBansService(BanRepository banRepository, BanValidatorService banValidatorService) {
    this.banRepository = banRepository;
    this.banValidatorService = banValidatorService;
  }

  @Override
  public List<Ban> handle(Optional<BanReason> banReason, Optional<BanStatus> status) {
    List<Ban> validatedBans = new ArrayList<>();
    if (banReason.isPresent() && status.isPresent()) {
      this.banRepository.findByReasonAndStatus(banReason.get(), status.get()).forEach(ban -> {
        if (!banValidatorService.isBanValid(ban.getBannedId())) {
          validatedBans.add(ban);
        }
      });
    } else if (banReason.isPresent() && !status.isPresent()) {
      this.banRepository.findAll().forEach(ban -> {
        if (ban.getReason().equals(banReason.get())) {
          validatedBans.add(ban);
        }
      });
    } else if (!banReason.isPresent() && status.isPresent()) {
      this.banRepository.findByStatus(status.get()).forEach(ban -> {
        if (!banValidatorService.isBanValid(ban.getBannedId())) {
          validatedBans.add(ban);
        }
      });
    } else if (!banReason.isPresent() && !status.isPresent()) {
      validatedBans.addAll(this.banRepository.findAll());
    }
    return validatedBans;
  }

}
