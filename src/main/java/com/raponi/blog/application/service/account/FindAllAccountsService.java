package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.usecase.account.FindAllAccountsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAllAccountsService implements FindAllAccountsUseCase {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  public FindAllAccountsService(AccountRepository accountRepository, AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.accountMapper = accountMapper;
  }

  @Override
  public List<AccountResponseDTO> handle() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    if (role.equals("ROLE_ADMIN"))
      return this.accountRepository.findAll().stream().map(accountMapper::toResponse).collect(Collectors.toList());
    else
      return this.accountRepository.findAllByActiveIsTrue().stream().map(accountMapper::toResponse)
          .collect(Collectors.toList());
  }

}
