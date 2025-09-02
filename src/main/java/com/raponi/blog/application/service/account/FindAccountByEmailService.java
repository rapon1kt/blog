package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByEmailUseCase;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAccountByEmailService implements FindAccountByEmailUseCase {

  private final AccountValidatorService accountValidatorService;
  private final AccountMapper mapper;

  public FindAccountByEmailService(AccountValidatorService accountValidatorService, AccountMapper mapper) {
    this.accountValidatorService = accountValidatorService;
    this.mapper = mapper;
  }

  @Override
  public AccountResponseDTO handle(String email) {
    Account account = this.accountValidatorService.getAccountByEmail(email);
    AccountResponseDTO responseAccount = mapper.toResponse(account);
    return responseAccount;
  }

}
