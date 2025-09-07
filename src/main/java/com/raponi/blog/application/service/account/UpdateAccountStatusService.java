package com.raponi.blog.application.service.account;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class UpdateAccountStatusService implements ChangeStatusUseCase {

  private AccountRepository accountRepository;
  private AccountValidatorService accountValidatorService;
  private AccountMapper accountMapper;

  public UpdateAccountStatusService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService, AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.accountMapper = accountMapper;
  }

  @Override
  public AccountResponseDTO handle(String accountId) {
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (!authorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findById(accountId).get();
    account.setActive(account.isActive() ? false : true);
    account.setModifiedAt(Instant.now());
    AccountResponseDTO responseAccount = this.accountMapper.toResponse(account);
    this.accountRepository.save(account);
    return responseAccount;
  }
}
