package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.AccountNotFound;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

  public AccountRepository accountRepository;

  public DeleteAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public String handle(String accountId) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    if (account.isPresent()) {
      this.accountRepository.deleteById(accountId);
      return "Conta deletada com sucesso";
    }
    throw new AccountNotFound("id equals " + accountId);
  }

}
