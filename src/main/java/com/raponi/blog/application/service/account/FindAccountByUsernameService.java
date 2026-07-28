package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByUsernameUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;

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
  public Account handle(String username) {
    String accountId = this.accountValidatorService.verifyAccountWithUsernameAndReturnId(username);
    if (accountId.equals(null))
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean isViewerBlocked = this.accountValidatorService.isBlocked(accountId);
    boolean isAccountBanned = this.accountValidatorService.isBanned(accountId);
    if (isViewerBlocked || isAccountBanned) {
      Account accountToBlockedViewer = new Account();
      accountToBlockedViewer.setUsername("unknow_profile_username");
      accountToBlockedViewer.setPicture("unknow_profile_picture");
      accountToBlockedViewer.setDescription("unknow_profile_description");
      return accountToBlockedViewer;
    }
    Account account = this.accountRepository.findByUsername(username).get();
    return account;
  }

}
