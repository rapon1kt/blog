package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.presentation.protocols.Http;

public interface FindAllAccountsUseCase {
  public List<Http.ResponseBody> handle();
}
