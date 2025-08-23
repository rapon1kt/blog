package com.raponi.blog.domain.usecase;

import com.raponi.blog.domain.model.Account;

public interface AccountValidatorUseCase {
  Account handle(String tokenId, String accountId, String password, String role);

  boolean isAdmin(String role);

  boolean passwordMatches(String password, String hashedPassword);
}
