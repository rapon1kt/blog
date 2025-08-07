package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAllAccountsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class FindAllAccountsService implements FindAllAccountsUseCase {

  private final AccountRepository accountRepository;

  public FindAllAccountsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Account> handle() {
    return this.accountRepository.findAll();
  }

}
