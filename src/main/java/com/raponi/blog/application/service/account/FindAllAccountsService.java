package com.raponi.blog.application.service.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAllAccountsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.PublicAccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAllAccountsService implements FindAllAccountsUseCase {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final AccountValidatorService accountValidatorService;

  public FindAllAccountsService(AccountRepository accountRepository, AccountMapper accountMapper,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.accountMapper = accountMapper;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<PublicAccountResponseDTO> handle() {
    if (this.accountValidatorService.isAdmin()) {
      return this.accountRepository.findAll().stream().map(accountMapper::toPublicAccountResponseDTO).toList();
    } else {
      List<Account> accounts = this.accountRepository.findAllByActiveIsTrue();
      List<Account> nonBlockedAccounts = new ArrayList<Account>();
      accounts.forEach(account -> {
        if (!this.accountValidatorService.isBlocked(account.getId()))
          nonBlockedAccounts.add(account);
      });
      return nonBlockedAccounts.stream().map(accountMapper::toPublicAccountResponseDTO).toList();
    }
  }

}
