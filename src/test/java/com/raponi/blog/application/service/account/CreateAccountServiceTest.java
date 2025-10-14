package com.raponi.blog.application.service.account;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.CreatedAccountResponseDTO;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.errors.MissingParamException;
import com.raponi.blog.presentation.mapper.AccountMapper;

public class CreateAccountServiceTest {

  private AccountRepository repository;
  private PasswordEncoderService encoder;
  private AccountMapper mapper;
  private CreateAccountService service;

  @BeforeEach
  void setup() {
    repository = mock(AccountRepository.class);
    encoder = mock(PasswordEncoderService.class);
    mapper = mock(AccountMapper.class);
    service = new CreateAccountService(repository, encoder, mapper);
  }

  @Test
  void mustReturnAnAccountWithSuccess() {
    CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
    requestDTO.setEmail("email@mail.com");
    requestDTO.setUsername("username");
    requestDTO.setPassword("12345678");

    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);
    when(encoder.encode(any())).thenReturn("hashed_password");
    Account saved = Account.create(requestDTO.getEmail(), requestDTO.getUsername(), "hashed_password");
    when(repository.save(any())).thenReturn(saved);

    CreatedAccountResponseDTO expectedResponse = new CreatedAccountResponseDTO();
    expectedResponse.setUsername(saved.getUsername());
    expectedResponse.setCreatedAt(saved.getCreatedAt());
    expectedResponse.setId(saved.getId());
    when(mapper.toCreatedResponse(any())).thenReturn(expectedResponse);

    CreatedAccountResponseDTO response = service.handle(requestDTO);

    verify(encoder).encode("12345678");

    ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    verify(repository).save(captor.capture());
    assertThat(captor.getValue().getPassword()).isEqualTo("hashed_password");

    assertThat(response).isNotNull();
    assertThat(response.getUsername()).isEqualTo("username");
  }

  @Test
  void mustThrowErrorWhenEmailIsAlreadyRegistred() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail("email@mail.com");
    request.setUsername("username");
    request.setPassword("12345678");

    when(repository.existsByEmail("email@mail.com")).thenReturn(true);

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(InvalidParamException.class)
        .hasMessage("E-mail already registred.");
  }

  @Test
  void mustThrowErrorWhenUsernameIsAlreadyInUse() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail("email@mail.com");
    request.setUsername("username");
    request.setPassword("12345678");

    when(repository.existsByUsername("username")).thenReturn(true);

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(InvalidParamException.class)
        .hasMessage("Username already registred.");
  }

  @Test
  void mustThrowErrorWhenEmailIsInvalid() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail("email@@invalid");
    request.setUsername("username");
    request.setPassword("12345678");

    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(InvalidParamException.class)
        .hasMessage("E-mail invalid.");
  }

  @Test
  void mustThrowErrorWhenPasswordIsShort() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail("email@mail.com");
    request.setUsername("username");
    request.setPassword("123");

    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(InvalidParamException.class)
        .hasMessage("The password must be at least 8 characters long.");
  }

  @Test
  void mustThrowErrorWhenUsernameIsShort() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail("email@mail.com");
    request.setUsername("us");
    request.setPassword("12345678");

    when(repository.existsByEmail(any())).thenReturn(false);
    when(repository.existsByUsername(any())).thenReturn(false);

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(InvalidParamException.class)
        .hasMessage("The username must be at least 3 characters long.");
  }

  @Test
  void mustThrowErrorWhenRequiredParamIsNull() {
    CreateAccountRequestDTO request = new CreateAccountRequestDTO();
    request.setEmail(null);
    request.setUsername("username");
    request.setPassword("12345678");

    assertThatThrownBy(() -> service.handle(request))
        .isInstanceOf(MissingParamException.class)
        .hasMessageContaining("email");
  }

}
