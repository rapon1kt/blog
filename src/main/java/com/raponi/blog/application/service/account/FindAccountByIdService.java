package com.raponi.blog.application.service.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String tokenId = auth.getName();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    Account account = this.accountValidatorService.getAccountByEmailOrAccountId(accountId, tokenId, role, null);
    return account.toResponseBody();
  }

}
