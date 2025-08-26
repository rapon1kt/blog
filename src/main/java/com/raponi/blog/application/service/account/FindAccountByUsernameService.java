package com.raponi.blog.application.service.account;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAccountByUsernameUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindAccountByUsernameService implements FindAccountByUsernameUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountByUsernameService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String username) {
    Optional<Account> acc = this.accountRepository.findByUsername(username);
    String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next()
        .getAuthority();
    Account account = this.accountValidatorService.verifyPresenceAndActive(acc, role);
    return account.toResponseBody();
  }

}
