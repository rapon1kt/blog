package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface ChangeStatusUseCase {
  public AccountResponseDTO handle(String accountId);
}
