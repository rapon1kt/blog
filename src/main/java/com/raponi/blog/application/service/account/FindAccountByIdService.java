package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountValidatorService accountValidatorService;
  private final AccountMapper mapper;

  public FindAccountByIdService(AccountValidatorService accountValidatorService, AccountMapper mapper) {
    this.accountValidatorService = accountValidatorService;
    this.mapper = mapper;
  }

  @Override
  public AccountResponseDTO handle(String accountId) {
    Account account = this.accountValidatorService.getAccountByAccountId(accountId);
    AccountResponseDTO responseAccount = mapper.toResponse(account);
    return responseAccount;
  }

}
