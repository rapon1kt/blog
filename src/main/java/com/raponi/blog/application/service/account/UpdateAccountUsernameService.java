package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.UpdateAccountUsernameUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.UpdateAccountUsernameRequestDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class UpdateAccountUsernameService implements UpdateAccountUsernameUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final AccountMapper accountMapper;

  public UpdateAccountUsernameService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService,
      AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.accountMapper = accountMapper;
  }

  @Override
  public AccountResponseDTO handle(String accountId, UpdateAccountUsernameRequestDTO requestDTO) {
    Account accountToUpdate = this.accountValidatorService.getAccountByAccountId(accountId);
    Account accountUpdated = accountToUpdate.updateAccountUsername(requestDTO.getUsername());
    AccountResponseDTO responseAccount = this.accountMapper.toResponse(accountUpdated);
    this.accountRepository.save(accountUpdated);
    return responseAccount;
  }
}
