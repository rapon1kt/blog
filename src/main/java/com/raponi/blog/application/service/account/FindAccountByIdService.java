package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {

  private final AccountValidatorService accountValidatorService;
  private final AccountRepository accountRepository;
  private final AccountMapper mapper;

  public FindAccountByIdService(AccountValidatorService accountValidatorService, AccountRepository accountRepository,
      AccountMapper mapper) {
    this.accountValidatorService = accountValidatorService;
    this.accountRepository = accountRepository;
    this.mapper = mapper;
  }

  @Override
  public AccountResponseDTO handle(String accountId) {
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findById(accountId).get();
    AccountResponseDTO responseAccount = mapper.toResponse(account);
    return responseAccount;
  }

}
