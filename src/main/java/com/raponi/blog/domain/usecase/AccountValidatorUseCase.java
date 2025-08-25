package com.raponi.blog.domain.usecase;

import com.raponi.blog.domain.model.Account;

public interface AccountValidatorUseCase {

  Account getAccountWithPasswordConfirmation(String accountId, String tokenId, String password, String role);

  Account getAccountByEmailOrAccountId(String accountId, String tokenId, String email, String role);

}
