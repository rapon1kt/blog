package com.raponi.blog.domain.usecase.account;

public interface ChangeAccountPasswordUseCase {
  public String handle(String accountId, String password, String newPassword);
}
