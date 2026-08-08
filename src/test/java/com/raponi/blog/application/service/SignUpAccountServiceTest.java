package com.raponi.blog.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.raponi.blog.application.command.SignUpAccountCommand;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.AccountRepository;
import com.raponi.blog.domain.port.PasswordEncoderPort;

public class SignUpAccountServiceTest {

  private SignUpAccountService service;
  private PasswordEncoderPort encoder;
  private AccountRepository repository;

  @BeforeEach
  void setup() {
    repository = mock(AccountRepository.class);
    encoder = mock(PasswordEncoderPort.class);
    service = new SignUpAccountService(repository, encoder);
  }

  @Test
  void mustSignUpWithSuccess() {
    // Creating command
    SignUpAccountCommand command = new SignUpAccountCommand(
        "test_email@test.com",
        "test_username",
        "test_password");

    // Defining expected responses
    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);
    when(encoder.encode(any())).thenReturn("passwordHash");

    Account saved = new Account(command.email(), command.username(), "passwordHash");
    when(repository.save(any())).thenReturn(saved);

    // Executing service with tested command
    Account serviceAccount = service.handle(command);

    // Ensure encoder are called with correct password
    verify(encoder).encode("test_password");

    // Ensure repository saved account with passwordHash
    ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    verify(repository).save(captor.capture());
    assertThat(captor.getValue().getPassword()).isEqualTo("passwordHash");

    // Ensure service returned a
    assertThat(serviceAccount).isNotNull();
    assertThat(serviceAccount.getUsername()).isEqualTo("test_username");

  }

}
