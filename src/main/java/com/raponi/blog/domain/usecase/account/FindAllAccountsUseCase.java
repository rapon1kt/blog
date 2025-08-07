package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.domain.model.Account;

public interface FindAllAccountsUseCase {
  public List<Account> handle();
}
