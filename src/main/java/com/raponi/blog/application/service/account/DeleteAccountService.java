package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

  public AccountRepository accountRepository;

  public DeleteAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public String handle(String accountId) {
    this.accountRepository.deleteById(accountId);
    return "Conta deletada com sucesso";
  }

}
