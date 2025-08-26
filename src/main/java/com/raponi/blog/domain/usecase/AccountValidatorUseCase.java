package com.raponi.blog.domain.usecase;

import com.raponi.blog.domain.model.Account;

public interface AccountValidatorUseCase {

  Account getAccountWithPasswordConfirmation(String accountId, String tokenId, String password, String role);

  Account getAccountByAccountId(String accountId, String tokenId, String role);

  Account getAccountByEmail(String tokenId, String email, String role);

  Account getAccountByUsername(String tokenId, String role, String username);

}
