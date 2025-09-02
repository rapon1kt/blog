package com.raponi.blog.application.service.account;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.JWTService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.usecase.account.LoginAccountUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class LoginAccountService implements LoginAccountUseCase {

  private final AccountRepository accountRepository;
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  public LoginAccountService(AccountRepository accountRepository, AuthenticationManager authenticationManager,
      JWTService jwtService) {
    this.accountRepository = accountRepository;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Override
  public String handle(Http.LoginBody bodyRequest) {
    Authentication authentication = this.authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(bodyRequest.username(), bodyRequest.password()));
    if (!authentication.isAuthenticated())
      throw new ResourceNotFoundException("This account cannot be found.");
    Account account = this.accountRepository.findByUsername(bodyRequest.username()).get();
    return jwtService.generateToken(bodyRequest.username(), account);
  }
}
