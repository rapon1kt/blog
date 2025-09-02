package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.CreateAccountRequestDTO;

public interface CreateAccountUseCase {
  public AccountResponseDTO handle(CreateAccountRequestDTO bodyRequest);
}
