package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountValidatorService accountValidatorService;

  public FindAccountByIdService(AccountValidatorService accountValidatorService) {
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String accountId) {
    Account account = this.accountValidatorService.getAccountByAccountId(accountId);
    return account.toResponseBody();
  }

}
