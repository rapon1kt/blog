package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface FindAccountByEmailUseCase {
  public AccountResponseDTO handle(String email);
}
