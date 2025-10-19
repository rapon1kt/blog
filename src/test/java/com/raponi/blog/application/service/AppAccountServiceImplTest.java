package com.raponi.blog.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.model.BanCategory;
import com.raponi.blog.domain.model.BanReason;
import com.raponi.blog.domain.model.BanStatus;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
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

  @Test
  void mustThrowWhenAccountIsPermanentlyBanned() {
    account.setBanned(true);
    Instant expiresAt = Instant.now().plus(Duration.ofDays(3650000));
    Ban ban = new Ban(BanCategory.SPAM_AND_MANIPULATION, BanReason.SPAM,
        "Admin Decision", "moderator_id", "id", expiresAt);
    ban.setStatus(BanStatus.PERMANENTLY_ACTIVE);
    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(banValidatorService.isBanValid(account.getId())).thenReturn(false);
    when(banRepository.findTopByBannedIdOrderByBannedAtDesc(account.getId())).thenReturn(Optional.of(ban));

    AccessDeniedException exception = assertThrows(AccessDeniedException.class,
        () -> appAccountService.loadUserByUsername("username"));

    assertTrue(exception.getMessage().contains("Your account is banned permanently. Reason: "));
  }

  @Test
  void mustThrowWhenAccountIsTemporarilyBanned() {
    account.setBanned(true);
    Instant expiresAt = Instant.now().plus(Duration.ofDays(2));
    Ban ban = new Ban(BanCategory.SPAM_AND_MANIPULATION, BanReason.SPAM,
        "Admin Decision", "moderator_id", "id", expiresAt);

    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(banValidatorService.isBanValid(account.getId())).thenReturn(false);
    when(banRepository.findTopByBannedIdOrderByBannedAtDesc(account.getId())).thenReturn(Optional.of(ban));

    AccessDeniedException ex = assertThrows(AccessDeniedException.class,
        () -> appAccountService.loadUserByUsername("username"));

    assertTrue(ex.getMessage().contains("Your account is temporarily banned until "));
  }

  @Test
  void mustReturnAccountIdByUsername() {
    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    String id = appAccountService.getAccountIdByUsername("username");
    assertEquals(account.getId(), id);
  }

  @Test
  void mustThrowWhenGettingAccountIdOfNonexistentUser() {
    when(accountRepository.findByUsername("ghost")).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> appAccountService.getAccountIdByUsername("ghost"));
  }

}
