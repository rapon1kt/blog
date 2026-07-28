package com.raponi.blog.application.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface FindAccountByIdUseCase {
  public Account handle(String accountId);
}
