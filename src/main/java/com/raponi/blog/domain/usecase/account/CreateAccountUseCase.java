package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface CreateAccountUseCase {
  public Account handle(Account newAccount);
}
