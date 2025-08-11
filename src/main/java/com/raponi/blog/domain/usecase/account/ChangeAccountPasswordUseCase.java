package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface ChangeAccountPasswordUseCase {
  public Account handle(String accountId, String newPassword);
}
