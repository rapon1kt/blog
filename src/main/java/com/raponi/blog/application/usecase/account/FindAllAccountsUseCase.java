package com.raponi.blog.application.usecase.account;

import java.util.List;

import com.raponi.blog.presentation.dto.PublicAccountResponseDTO;

public interface FindAllAccountsUseCase {
  public List<PublicAccountResponseDTO> handle();
}
