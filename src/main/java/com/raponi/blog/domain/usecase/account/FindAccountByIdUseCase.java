package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.protocols.Http;

public interface FindAccountByIdUseCase {
  public Http.ResponseBody handle(String accountId);
}
