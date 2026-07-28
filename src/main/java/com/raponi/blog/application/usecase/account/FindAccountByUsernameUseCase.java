package com.raponi.blog.application.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface FindAccountByUsernameUseCase {
  public Account handle(String username);
}
