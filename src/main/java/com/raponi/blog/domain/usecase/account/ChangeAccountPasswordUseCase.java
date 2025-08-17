package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.protocols.Http;

public interface ChangeAccountPasswordUseCase {
  public Http.ResponseBody handle(String accountId, String newPassword);
}
