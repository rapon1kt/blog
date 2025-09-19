package com.raponi.blog.application.service.account;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountByUsernameUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.PublicAccountResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class FindAccountByUsernameService implements FindAccountByUsernameUseCase {

  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final AccountMapper mapper;

  public FindAccountByUsernameService(AccountRepository accountRepository,
      AccountValidatorService accountValidatorService, AccountMapper mapper) {
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.mapper = mapper;
  }

  @Override
  public PublicAccountResponseDTO handle(String username) {
    String accountId = this.accountValidatorService.verifyAccountWithUsernameAndReturnId(username);
    if (accountId.equals(null))
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean isViewerBlocked = this.accountValidatorService.isBlocked(accountId);
    if (isViewerBlocked) {
      PublicAccountResponseDTO accountToBlockedViwer = new PublicAccountResponseDTO();
      accountToBlockedViwer.setUsername("unknow_profile_username");
      accountToBlockedViwer.setPicture("unknow_profile_picture");
      accountToBlockedViwer.setDescription("unknow_profile_description");
      accountToBlockedViwer.setCreatedAt(Instant.now());
      return accountToBlockedViwer;
    }
    Account account = this.accountRepository.findByUsername(username).get();
    PublicAccountResponseDTO responseAccount = mapper.toPublicAccountResponseDTO(account);
    return responseAccount;
  }

}
