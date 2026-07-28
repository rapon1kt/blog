package com.raponi.blog.application.usecase.account;

import com.raponi.blog.domain.model.BlockResult;

public interface BlockUnblockAccountUseCase {
  public BlockResult handle(String accountId, String blockedId);
}
