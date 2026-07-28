package com.raponi.blog.application.service.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.raponi.blog.application.usecase.account.LoginAccountCommand;
import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.port.TokenGeneratorService;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginAccountServiceTest {

  private BanRepository banRepository;
  private AccountRepository accountRepository;
  private BanValidatorService banValidatorService;
  private LoginAccountService loginAccountService;
  private PasswordEncoderService passwordEncoderService;
  private TokenGeneratorService tokenGeneratorService;

  @BeforeEach
  void setup() {
    banRepository = mock(BanRepository.class);
    accountRepository = mock(AccountRepository.class);
    banValidatorService = mock(BanValidatorService.class);

    when(banValidatorService.isBanValid(any())).thenReturn(true);

    tokenGeneratorService = mock(TokenGeneratorService.class);
    passwordEncoderService = mock(PasswordEncoderService.class);

    loginAccountService = new LoginAccountService(
        accountRepository,
        passwordEncoderService,
        tokenGeneratorService,
        banRepository,
        banValidatorService);
  }

  @Test
  void mustLoginSuccessfullyAndReturnToken() {
    LoginAccountCommand command = new LoginAccountCommand("username", "12345678");
    Account account = Account.create("email@mail.com", "username", "hashed_password");

    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(passwordEncoderService.matches("12345678", "hashed_password")).thenReturn(true);
    when(tokenGeneratorService.generateToken(account)).thenReturn("mocked_jwt_token");

    System.out.println(command);

    String token = loginAccountService.handle(command);

    assertNotNull(token);
    assertEquals("mocked_jwt_token", token);

    verify(accountRepository, times(1)).findByUsername("username");
    verify(passwordEncoderService, times(1)).matches("12345678", "hashed_password");
    verify(tokenGeneratorService, times(1)).generateToken(account);
  }

  @Test
  void mustThrowIfPasswordIsIncorrect() {
    LoginAccountCommand command = new LoginAccountCommand("username", "wrong_password");
    Account account = Account.create("email@mail.com", "username", "hashed_password");

    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(passwordEncoderService.matches("wrong_password", "hashed_password")).thenReturn(false);

    InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
        () -> loginAccountService.handle(command));

    assertEquals("Invalid username or password.", exception.getMessage());
  }

  @Test
  void mustThrowIfAccountNotFoundInRepository() {
    LoginAccountCommand command = new LoginAccountCommand("non_existent_username", "12345678");

    when(accountRepository.findByUsername("non_existent_username")).thenReturn(Optional.empty());

    InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
        () -> loginAccountService.handle(command));

    assertEquals("Invalid username or password.", exception.getMessage());
  }
}