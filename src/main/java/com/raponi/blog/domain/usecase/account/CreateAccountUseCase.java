package com.raponi.blog.domain.usecase.account;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.protocols.Http;

public interface CreateAccountUseCase {
  public Account handle(Http.RegisterBody bodyRequest);
}
