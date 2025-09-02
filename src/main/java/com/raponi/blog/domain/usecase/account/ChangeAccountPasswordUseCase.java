package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.UpdateAccountPasswordRequestDTO;

public interface ChangeAccountPasswordUseCase {
  public String handle(String accountId, UpdateAccountPasswordRequestDTO requestDTO);
}
