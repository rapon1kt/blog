package com.raponi.blog.application.service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.ChangeAccountPasswordUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.dto.UpdateAccountPasswordRequestDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class ChangeAccountPasswordService implements ChangeAccountPasswordUseCase {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountValidatorService accountValidatorService;

  public ChangeAccountPasswordService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
      AccountValidatorService accountValidatorService) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, UpdateAccountPasswordRequestDTO requestDTO) {
    Account acc = this.accountRepository.findById(accountId)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found."));
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean correctOldPassword = passwordEncoder.matches(acc.getPassword(), requestDTO.getPassword());
    if (!correctOldPassword)
      throw new InvalidParamException("Your password does not match the system password.");
    verifyNewPassword(requestDTO.getNewPassword(), requestDTO.getPassword());
    acc.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
    this.accountRepository.save(acc);
    return "Password changed with success!";
  }

  private void verifyNewPassword(String newPassword, String password) {
    if (newPassword.isBlank() || newPassword.length() < 8) {
      throw new InvalidParamException("The new password must be at least 8 characters long.");
    } else if (newPassword.equals(password)) {
      throw new InvalidParamException("The new password must be different from the old one.");
    }
  }
}
