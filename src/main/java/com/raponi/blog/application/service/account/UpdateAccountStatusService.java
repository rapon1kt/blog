package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class UpdateAccountStatusService implements ChangeStatusUseCase {

  private AccountRepository accountRepository;

  public UpdateAccountStatusService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account handle(String accountId, boolean newActiveStatus) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    if (account.isPresent()) {
      return this.accountRepository.save(account.get().changeStatus(newActiveStatus));
    }
    throw new AccountNotFound("id equals " + accountId);
  }
}
