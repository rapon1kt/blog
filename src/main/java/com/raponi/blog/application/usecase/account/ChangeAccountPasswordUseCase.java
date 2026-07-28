package com.raponi.blog.application.usecase.account;

public interface ChangeAccountPasswordUseCase {
  public String handle(String accountId, ChangeAccountPasswordCommand command);
}
