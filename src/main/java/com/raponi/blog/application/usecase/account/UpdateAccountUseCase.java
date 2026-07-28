package com.raponi.blog.application.usecase.account;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.raponi.blog.domain.model.Account;

public interface UpdateAccountUseCase {
  public Account handle(String accountId, UpdateAccountCommand command,
      MultipartFile image) throws IOException;
}
