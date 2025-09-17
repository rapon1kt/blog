package com.raponi.blog.application.usecase;

public interface AccountValidatorUseCase {

  boolean verifyAccountWithAccountId(String accountId);

  boolean verifyAccountWithEmail(String email);

  boolean verifyAccountWithUsername(String username);

}
