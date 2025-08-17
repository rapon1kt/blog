package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.protocols.Http;

public interface CreateAccountUseCase {
  public Http.ResponseBody handle(Http.RegisterBody bodyRequest);
}
