package com.raponi.blog.domain.usecase.account;

public interface DeleteAccountUseCase {
  public String handle(String tokenId, String accountId, String role, String password);
}
