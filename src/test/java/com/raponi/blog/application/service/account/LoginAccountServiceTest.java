package com.raponi.blog.application.service.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.raponi.blog.application.service.JWTService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.LoginAccountRequestDTO;
import com.raponi.blog.presentation.dto.LoginAccountResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.AccountMapper;

public class LoginAccountServiceTest {

  private AccountMapper accountMapper;
  private AccountRepository accountRepository;
  private AuthenticationManager authenticationManager;
  private JWTService jwtService;
  private LoginAccountService loginAccountService;

  @BeforeEach
  void setup() {
    accountMapper = mock(AccountMapper.class);
    accountRepository = mock(AccountRepository.class);
    authenticationManager = mock(AuthenticationManager.class);
    jwtService = mock(JWTService.class);
    loginAccountService = new LoginAccountService(accountMapper, accountRepository, authenticationManager, jwtService);
  }

  @Test
  void mustLoginSuccessfullyAndReturnToken() {
    LoginAccountRequestDTO request = new LoginAccountRequestDTO();
    request.setUsername("username");
    request.setPassword("12345678");
    Authentication mockAuth = mock(Authentication.class);
    when(mockAuth.isAuthenticated()).thenReturn(true);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);
    Account account = Account.create("email@mail.com", "username", "hashed_password");
    when(accountRepository.findByUsername("username")).thenReturn(Optional.of(account));
    when(jwtService.generateToken(eq("username"), eq(account))).thenReturn("mocked_jwt_token");
    LoginAccountResponseDTO response = loginAccountService.handle(request);
    assertNotNull(response);
    assertEquals("mocked_jwt_token", response.getToken());

    verify(authenticationManager, times(1))
        .authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(accountRepository, times(1)).findByUsername("username");
    verify(jwtService, times(1)).generateToken("username", account);
  }

  @Test
  void mustThrowIfAuthenticationFails() {
    LoginAccountRequestDTO request = new LoginAccountRequestDTO();
    request.setUsername("username");
    request.setPassword("12345678");

    Authentication mockAuth = mock(Authentication.class);

    when(mockAuth.isAuthenticated()).thenReturn(false);
    when(authenticationManager.authenticate(any())).thenReturn(mockAuth);

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
        () -> loginAccountService.handle(request));

    assertEquals("This account cannot be found.", exception.getMessage());
  }

  @Test
  void mustThrowIfAccountNotFoundInRepository() {
    LoginAccountRequestDTO request = new LoginAccountRequestDTO();
    request.setUsername("non_existent_username");
    request.setPassword("12345678");
    Authentication mockAuth = mock(Authentication.class);

    when(mockAuth.isAuthenticated()).thenReturn(true);
    when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
    when(accountRepository.findByUsername("non_existent_username")).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
        () -> loginAccountService.handle(request));
    assertEquals("This account cannot be found.", exception.getMessage());
  }

}
