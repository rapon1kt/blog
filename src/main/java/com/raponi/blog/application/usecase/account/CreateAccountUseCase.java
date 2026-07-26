package com.raponi.blog.application.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface CreateAccountUseCase {
  public Account handle(CreateAccountCommand command);
}
