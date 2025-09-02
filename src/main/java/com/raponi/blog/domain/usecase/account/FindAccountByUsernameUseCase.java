package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface FindAccountByUsernameUseCase {
  public AccountResponseDTO handle(String username);
}
