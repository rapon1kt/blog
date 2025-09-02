package com.raponi.blog.application.service.account;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.PasswordEncoderService;
import com.raponi.blog.domain.usecase.account.CreateAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.CreateAccountRequestDTO;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.errors.MissingParamException;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class CreateAccountService implements CreateAccountUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoderService passwordEncoderService;
  private final AccountMapper accountMapper;

  public CreateAccountService(AccountRepository accountRepository, PasswordEncoderService passwordEncoderService,
      AccountMapper accountMapper) {
    this.accountRepository = accountRepository;
    this.passwordEncoderService = passwordEncoderService;
    this.accountMapper = accountMapper;
  }

  @Override
  public AccountResponseDTO handle(CreateAccountRequestDTO requestDTO) {
    validateRequest(requestDTO);
    String hashedPassword = this.passwordEncoderService.encode(requestDTO.getPassword());
    Account account = Account.create(requestDTO.getEmail(), requestDTO.getUsername(), hashedPassword);
    AccountResponseDTO responseAccount = this.accountMapper.toResponse(account);
    this.accountRepository.save(account);
    return responseAccount;
  }

  private void validateRequest(CreateAccountRequestDTO requestDTO) {
    String[] requiredParams = { "email", "username", "password" };
    for (String param : requiredParams) {
      if (isNullOrEmpty(getFieldValue(requestDTO, param))) {
        throw new MissingParamException(param);
      }
    }
    validateEmail(requestDTO.getEmail());
    validateUsername(requestDTO.getUsername());
    validatePassword(requestDTO.getPassword());
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

  private Object getFieldValue(CreateAccountRequestDTO body, String fieldName) {
    try {
      var field = body.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(body);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      return null;
    }
  }
}