package com.raponi.blog.application.usecase.account;

import com.raponi.blog.presentation.dto.DeleteAccountRequestDTO;

public interface DeleteAccountUseCase {
  public String handle(String accountId, DeleteAccountRequestDTO request);
}
