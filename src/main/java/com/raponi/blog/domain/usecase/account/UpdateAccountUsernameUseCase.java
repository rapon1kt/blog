package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.UpdateAccountUsernameRequestDTO;

public interface UpdateAccountUsernameUseCase {
  public AccountResponseDTO handle(String accountId, UpdateAccountUsernameRequestDTO requestDTO);
}
