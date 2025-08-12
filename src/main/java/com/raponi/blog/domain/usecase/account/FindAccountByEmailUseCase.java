package com.raponi.blog.domain.usecase.account;

import java.util.Optional;

import com.raponi.blog.domain.model.Account;

public interface FindAccountByEmailUseCase {
  public Optional<Account> handle(String email);
}
