package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface ChangeStatusUseCase {
  public Account handle(String accountId, boolean newActiveStatus);
}
