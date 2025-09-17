package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByEmailUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAccountByEmailService implements FindAccountByEmailUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final AccountMapper mapper;

  public FindAccountByEmailService(AccountRepository accountRepository, AccountValidatorService accountValidatorService,
      AccountMapper mapper) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.mapper = mapper;
  }

  @Override
  public AccountResponseDTO handle(String email) {
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithEmail(email);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findByEmail(email).get();
    AccountResponseDTO responseAccount = mapper.toResponse(account);
    return responseAccount;
  }

}
