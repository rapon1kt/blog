package com.raponi.blog.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raponi.blog.application.command.SignUpAccountCommand;
import com.raponi.blog.application.usecase.SignInAccountUseCase;
import com.raponi.blog.application.usecase.SignUpAccountUseCase;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.infrastructure.security.JWTAdapter;
import com.raponi.blog.presentation.dto.request.SignUpAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.SignUpAccountResponseDTO;
import com.raponi.blog.presentation.mapper.AccountPresentationMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @MockitoBean
  private AccountPresentationMapper mapper;

  @MockitoBean
  private SignUpAccountUseCase signUpUseCase;

  @MockitoBean
  private SignInAccountUseCase signInUseCase;

  @MockitoBean
  private JWTAdapter jwtAdapter;

  @Test
  void mustReturnCorrectDTO_WhenSignedUpCorrectly() throws Exception {
    // Creating request
    SignUpAccountRequestDTO request = new SignUpAccountRequestDTO(
        "test_email@test.com",
        "test_username",
        "test_password");

    // Creating mocked mapper responses
    Account mockedAccount = new Account(
        "test_email@test.com",
        "test_username",
        "test_password");

    SignUpAccountCommand mockedCommand = new SignUpAccountCommand(
        "test_email@test.com",
        "test_username",
        "test_password");

    SignUpAccountResponseDTO responseDTO = new SignUpAccountResponseDTO(
        null,
        "test_email@test.com",
        "test_username",
        Instant.now());

    // Defining expected responses of mapper and service
    when(mapper.toSignUpCommand(any(SignUpAccountRequestDTO.class))).thenReturn(mockedCommand);
    when(signUpUseCase.handle(any(SignUpAccountCommand.class))).thenReturn(mockedAccount);
    when(mapper.toSignUpResponse(any(Account.class))).thenReturn(responseDTO);

    mockMvc.perform(post("/auth/sign-up")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(mockedAccount.getId()))
        .andExpect(jsonPath("$.email").value(mockedAccount.getEmail()))
        .andExpect(jsonPath("$.username").value(mockedAccount.getUsername()));

  }

}
