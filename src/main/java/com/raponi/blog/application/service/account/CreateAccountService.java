package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.usecase.account.CreateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.MissingParamError;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class CreateAccountService implements CreateAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoderService;

  public CreateAccountService(AccountRepository accountRepository, PasswordEncoderService passwordEncoderService) {
    this.accountRepository = accountRepository;
    this.passwordEncoderService = passwordEncoderService;
  }

  @Override
  public Account handle(Http.Body bodyRequest) {
    validateRequest(bodyRequest);
    String hashedPassword = this.passwordEncoderService.encode(bodyRequest.password());
    Account account = Account.create(bodyRequest.email(), bodyRequest.username(), hashedPassword);
    return this.accountRepository.save(account);
  }

  private void validateRequest(Http.Body bodyRequest) {
    String[] requiredParams = { "email", "username", "password" };
    for (String param : requiredParams) {
      if (isNullOrEmpty(getFieldValue(bodyRequest, param))) {
        throw new MissingParamError(param);
      }
    }
    validateEmail(bodyRequest.email());
    validateUsername(bodyRequest.username());
    validatePassword(bodyRequest.password());
  }

  private void validateUsername(String username) {
    if (this.accountRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username já cadastrado.");
    } else if (username.length() < 3) {
      throw new IllegalArgumentException("O username deve ter pelo menos 3 caracteres.");
    }
  }

  private void validateEmail(String email) {
    if (this.accountRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("E-mail já cadastrado.");
    } else if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
      throw new IllegalArgumentException("E-mail inválido.");
    }
  }

  private void validatePassword(String password) {
    if (password.length() < 8) {
      throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres.");
    }
  }

  private boolean isNullOrEmpty(Object value) {
    return value == null || value.toString().trim().isEmpty();
  }

  private Object getFieldValue(Http.Body body, String fieldName) {
    try {
      var field = body.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(body);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return null;
    }
  }
}