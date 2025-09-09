package com.raponi.blog.application.usecase;

import com.raponi.blog.domain.model.Account;

public interface AccountValidatorUseCase {

  Account getAccountWithPasswordConfirmation(String accountId, String password);

  Account getAccountByAccountId(String accountId);

  Account getAccountByEmail(String email);

  Account getAccountByUsername(String username);

}
