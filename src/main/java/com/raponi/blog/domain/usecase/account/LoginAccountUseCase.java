package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.presentation.protocols.Http;

public interface LoginAccountUseCase {
  public String handle(Http.LoginBody bodyRequest);
}
