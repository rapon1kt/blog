package com.raponi.blog.application.usecase.ban;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;

public interface FindBansUseCase {
  public List<Ban> handle(Optional<BanReason> banReason, Optional<BanStatus> status);
}
