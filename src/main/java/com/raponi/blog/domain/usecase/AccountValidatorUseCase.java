package com.raponi.blog.domain.usecase;

import com.raponi.blog.domain.model.Account;

public interface AccountValidatorUseCase {

  Account verifyWithPasswordInRequest(String tokenId, String accountId, String password, String role);

  Account verifyWithEmailOrAccountId(String accountId, String tokenId, String role, String email);

  boolean isAdmin(String role);

  boolean passwordMatches(String password, String hashedPassword);
}
