package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.CreatedAccountResponseDTO;

public interface CreateAccountUseCase {
  public CreatedAccountResponseDTO handle(CreateAccountRequestDTO requestDTO);
}
