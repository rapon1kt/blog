package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.FindAllAccountsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindAllAccountsService implements FindAllAccountsUseCase {

  private final AccountRepository accountRepository;

  public FindAllAccountsService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Http.ResponseBody> handle() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    if (role.equals("ROLE_ADMIN"))
      return this.accountRepository.findAll().stream().map(Account::toResponseBody).collect(Collectors.toList());
    else
      return this.accountRepository.findAllByActiveIsTrue().stream().map(Account::toResponseBody)
          .collect(Collectors.toList());
  }

}
