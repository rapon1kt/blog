package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.LoginAccountRequestDTO;
import com.raponi.blog.presentation.dto.LoginAccountResponseDTO;

public interface LoginAccountUseCase {
  public LoginAccountResponseDTO handle(LoginAccountRequestDTO requestDTO);
}
