package com.raponi.blog.domain.usecase.account;

public interface ChangeAccountPasswordUseCase {
  public String handle(String accountId, String role, String tokenId, String password, String newPassword);
}
