package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface FindAccountByEmailUseCase {
  public AccountResponseDTO handle(String email);
}
