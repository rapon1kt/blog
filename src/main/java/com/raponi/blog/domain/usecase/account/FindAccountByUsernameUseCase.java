package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.dto.PublicAccountResponseDTO;

public interface FindAccountByUsernameUseCase {
  public PublicAccountResponseDTO handle(String username);
}
