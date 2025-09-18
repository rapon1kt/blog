package com.raponi.blog.application.usecase.account;

public interface BlockUnblockAccountUseCase {
  public String handle(String accountId, String blockedId);
}
