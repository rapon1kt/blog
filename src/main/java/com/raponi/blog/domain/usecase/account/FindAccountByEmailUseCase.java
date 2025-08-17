package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.protocols.Http;

public interface FindAccountByEmailUseCase {
  public Http.ResponseBody handle(String email);
}
