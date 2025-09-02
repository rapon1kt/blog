package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.usecase.account.CreateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.errors.MissingParamException;
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
  public Http.ResponseBody handle(Http.RegisterBody bodyRequest) {
    validateRequest(bodyRequest);
    String hashedPassword = this.passwordEncoderService.encode(bodyRequest.password());
    Account account = Account.create(bodyRequest.email(), bodyRequest.username(), hashedPassword);
    this.accountRepository.save(account);
    return account.toResponseBody();
  }

  private void validateRequest(Http.RegisterBody bodyRequest) {
    String[] requiredParams = { "email", "username", "password" };
    for (String param : requiredParams) {
      if (isNullOrEmpty(getFieldValue(bodyRequest, param))) {
        throw new MissingParamException(param);
      }
    }
    validateEmail(bodyRequest.email());
    validateUsername(bodyRequest.username());
    validatePassword(bodyRequest.password());
  }

  private void validateUsername(String username) {
    if (this.accountRepository.existsByUsername(username)) {
      throw new InvalidParamException("Username already registred.");
    } else if (username.length() < 3) {
      throw new InvalidParamException("The username must be at least 3 characters long.");
    }
  }

  private void validateEmail(String email) {
    if (this.accountRepository.existsByEmail(email)) {
      throw new InvalidParamException("E-mail already registred.");
    } else if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
      throw new InvalidParamException("E-mail invalid.");
    }
  }

  private void validatePassword(String password) {
    if (password.length() < 8) {
      throw new InvalidParamException("The password must be at least 8 characters long.");
    }
  }

  private boolean isNullOrEmpty(Object value) {
    return value == null || value.toString().trim().isEmpty();
  }

  private Object getFieldValue(Http.RegisterBody body, String fieldName) {
    try {
      var field = body.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(body);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return null;
    }
  }
}