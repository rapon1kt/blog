package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.domain.model.Account;

public interface UpdateAccountUseCase {
  public Account handle(String accountId, Account newAccountInfos);
}
