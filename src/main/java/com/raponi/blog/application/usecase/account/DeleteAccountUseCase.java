package com.raponi.blog.application.usecase.account;

public interface DeleteAccountUseCase {
  public String handle(String accountId, String password);
}
