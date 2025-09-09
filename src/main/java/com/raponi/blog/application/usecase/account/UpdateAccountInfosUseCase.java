package com.raponi.blog.application.usecase.account;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.UpdateAccountInfosRequestDTO;

public interface UpdateAccountInfosUseCase {
  public AccountResponseDTO handle(String accountId, UpdateAccountInfosRequestDTO requestDTO,
      MultipartFile image) throws IOException;
}
