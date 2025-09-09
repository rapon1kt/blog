package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface FindAccountByIdUseCase {
  public AccountResponseDTO handle(String accountId);
}
