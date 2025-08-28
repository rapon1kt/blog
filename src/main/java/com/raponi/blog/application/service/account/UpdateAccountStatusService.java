package com.raponi.blog.application.service.account;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.ChangeStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class UpdateAccountStatusService implements ChangeStatusUseCase {

  private AccountRepository accountRepository;
  private AccountValidatorService accountValidatorService;

  public UpdateAccountStatusService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.ResponseBody handle(String accountId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    String tokenId = auth.getName();
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId, tokenId, role);
    if (authorized) {
      Account account = this.accountRepository.findById(accountId).get();
      return this.accountRepository.save(account.changeStatus()).toResponseBody();
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }
}
