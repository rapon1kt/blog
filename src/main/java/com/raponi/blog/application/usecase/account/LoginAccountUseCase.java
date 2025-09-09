package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.LoginAccountRequestDTO;

public interface LoginAccountUseCase {
  public String handle(LoginAccountRequestDTO requestDTO);
}
