package com.raponi.blog.application.service.account;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.raponi.blog.application.usecase.account.CreateAccountCommand;
import com.raponi.blog.domain.exception.EmailAlreadyRegisteredException;
import com.raponi.blog.domain.exception.UsernameAlreadyRegisteredException;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class CreateAccountServiceTest {

  private AccountRepository repository;
  private CreateAccountService service;
  private PasswordEncoderService encoder;

  @BeforeEach
  void setup() {
    repository = mock(AccountRepository.class);
    encoder = mock(PasswordEncoderService.class);
    service = new CreateAccountService(repository, encoder);
  }

  @Test
  void mustReturnAnAccountWithSuccess() {
    CreateAccountCommand command = new CreateAccountCommand("username", "email@mail.com", "12345678");

    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);
    when(encoder.encode(any())).thenReturn("hashed_password");

    Account saved = Account.create(command.email(), command.username(), "hashed_password");
    when(repository.save(any())).thenReturn(saved);

    Account response = service.handle(command);

    verify(encoder).encode("12345678");

    ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    verify(repository).save(captor.capture());
    assertThat(captor.getValue().getPassword()).isEqualTo("hashed_password");

    assertThat(response).isNotNull();
    assertThat(response.getUsername()).isEqualTo("username");
  }

  @Test
  void mustThrowErrorWhenEmailIsAlreadyRegistred() {
    CreateAccountCommand command = new CreateAccountCommand("username", "email@mail.com", "12345678");

    when(repository.existsByEmail("email@mail.com")).thenReturn(true);

    assertThatThrownBy(() -> service.handle(command))
        .isInstanceOf(EmailAlreadyRegisteredException.class)
        .hasMessage("E-mail already registered.");
  }

  @Test
  void mustThrowErrorWhenUsernameIsAlreadyInUse() {
    CreateAccountCommand command = new CreateAccountCommand("username", "email@mail.com", "12345678");

    when(repository.existsByUsername("username")).thenReturn(true);

    assertThatThrownBy(() -> service.handle(command))
        .isInstanceOf(UsernameAlreadyRegisteredException.class)
        .hasMessage("Username already registered.");
  }
}
