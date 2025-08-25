package com.raponi.blog.application.service.account;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByEmailUseCase;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindAccountByEmailService implements FindAccountByEmailUseCase {

  private final AccountValidatorService accountValidatorService;

  public FindAccountByEmailService(AccountValidatorService accountValidatorService) {
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String email) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String tokenId = auth.getName();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    Account acc = this.accountValidatorService.getAccountByEmailOrAccountId(null, tokenId, role, email);
    return acc.toResponseBody();
  }

}
