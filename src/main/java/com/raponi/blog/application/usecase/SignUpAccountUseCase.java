package com.raponi.blog.application.usecase;

import com.raponi.blog.application.command.SignUpAccountCommand;
import com.raponi.blog.domain.model.Account;

public interface SignUpAccountUseCase {
  public Account handle(SignUpAccountCommand command);
}
