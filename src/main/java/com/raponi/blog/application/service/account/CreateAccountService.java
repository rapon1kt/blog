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
    String[] requiredFields = { "email", "username", "password" };
    for (String field : requiredFields) {
      if (isNullOrEmpty(getFieldValue(bodyRequest, field))) {
        throw new MissingParamError(field);
      }
    }

    String hashedPassword = this.passwordEncoderService.encode(bodyRequest.password());
    Account account = Account.create(bodyRequest.email(), bodyRequest.username(), hashedPassword);
    return this.accountRepository.save(account);
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