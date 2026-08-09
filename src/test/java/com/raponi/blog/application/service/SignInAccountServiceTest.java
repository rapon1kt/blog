package com.raponi.blog.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.raponi.blog.application.command.SignInAccountCommand;
import com.raponi.blog.application.result.SignInAccountResult;
import com.raponi.blog.domain.exception.InvalidCredentialsException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.AccountRepository;
import com.raponi.blog.domain.port.PasswordEncoderPort;
import com.raponi.blog.domain.port.TokenGeneratorPort;

public class SignInAccountServiceTest {

  private PasswordEncoderPort encoder;
  private SignInAccountService service;
  private AccountRepository repository;
  private TokenGeneratorPort tokenGenerator;

  @BeforeEach
  void setup() {
    encoder = mock(PasswordEncoderPort.class);
    repository = mock(AccountRepository.class);
    tokenGenerator = mock(TokenGeneratorPort.class);
    service = new SignInAccountService(repository, tokenGenerator, encoder);
  }

  @Test
  void mustSignInAccountWithSuccess() {
    // Creating command
    SignInAccountCommand command = new SignInAccountCommand(
        "test_email@test.com",
        "test_password");

    // Creating mocked account
    Account mockedAccount = new Account(
        "test_email@test.com",
        "test_username",
        "passwordHash");

    // Defining expected responses
    when(repository.findByEmail(any())).thenReturn(Optional.of(mockedAccount));
    when(encoder.matches(any(), any())).thenReturn(true);
    when(tokenGenerator.generateToken(any())).thenReturn("test_jwt");

    // Executing service with tested command
    SignInAccountResult result = service.handle(command);

    assertNotNull(result);
    assertEquals(mockedAccount, result.getAccount());
    assertEquals("test_jwt", result.getToken());

    // Ensure repository was called at correct time with the correct email
    verify(repository, times(1)).findByEmail(command.email());

    // Ensure password encoder was called once with right params
    verify(encoder, times(1)).matches(command.password(), mockedAccount.getPassword());

    // Ensure token generator was called once with db account
    verify(tokenGenerator, times(1)).generateToken(mockedAccount);
  }

  @Test
  void mustThrowIfEmailIsNotRegistered() {
    // Creating command
    SignInAccountCommand command = new SignInAccountCommand(
        "not_registered_email@test.com",
        "test_password");

    // Defining expected responses
    when(repository.findByEmail(command.email())).thenReturn(Optional.empty());

    // Executing service with invalid email
    assertThatThrownBy(() -> service.handle(command))
        .isInstanceOf(InvalidCredentialsException.class)
        .hasMessage("Invalid credentials.");
  }

  @Test
  void mustThrowIfPasswordIsInvalid() {
    // Creating command
    SignInAccountCommand command = new SignInAccountCommand(
        "test_email@test.com",
        "invalid_password");

    // Defining expected responses
    when(repository.findByEmail(any())).thenReturn(Optional.empty());
    when(encoder.matches(any(), any())).thenReturn(false);

    // Executing service with invalid password
    assertThatThrownBy(() -> service.handle(command))
        .isInstanceOf(InvalidCredentialsException.class)
        .hasMessage("Invalid credentials.");
  }

}
