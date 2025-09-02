package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.presentation.dto.AccountResponseDTO;

public interface FindAllAccountsUseCase {
  public List<AccountResponseDTO> handle();
}
