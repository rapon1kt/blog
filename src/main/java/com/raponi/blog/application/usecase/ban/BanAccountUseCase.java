package com.raponi.blog.application.usecase.ban;

import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.presentation.dto.BanAccountRequestDTO;

public interface BanAccountUseCase {

  Ban handle(String moderatorId, String bannedId, BanReason reason, BanAccountRequestDTO requestDTO);

}
