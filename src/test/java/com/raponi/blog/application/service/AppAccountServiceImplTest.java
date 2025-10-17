package com.raponi.blog.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

public class AppAccountServiceImplTest {

  private AccountRepository accountRepository;
  private BanRepository banRepository;
  private BanValidatorService banValidatorService;
  private AppAccountServiceImpl appAccountService;
  private Account account;

  @BeforeEach
  void setup() {
    accountRepository = mock(AccountRepository.class);
    banRepository = mock(BanRepository.class);
    banValidatorService = mock(BanValidatorService.class);
    appAccountService = new AppAccountServiceImpl(accountRepository, banRepository, banValidatorService);
    account = Account.create("email@mail.com", "username", "hashed_password");
    account.setId("id");
  }

  @Test
  void mustThrowWhenAccountNotFound() {
    when(accountRepository.findByUsername("non_existent")).thenReturn(Optional.empty());
    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
        () -> appAccountService.loadUserByUsername("non_existent"));
    assertEquals("This account cannot be found!", exception.getMessage());
  }

  @Test
  void mustLoadUserSuccessfullyWhenNotBanned() {
    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(banValidatorService.isBanValid(account.getId())).thenReturn(true);
    UserDetails user = appAccountService.loadUserByUsername("username");
    assertEquals("username", user.getUsername());
    assertEquals("hashed_password", user.getPassword());
  }

}
