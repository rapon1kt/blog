package com.raponi.blog.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class AppAccountServiceImpl implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Account> account = this.accountRepository.findByUsername(username);

    if (account.isPresent()) {
      var accountObj = account.get();
      return User.builder()
          .username(accountObj.getUsername())
          .password(accountObj.getPassword())
          .build();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  public String getAccountIdByUsername(String username) {
    Optional<Account> account = this.accountRepository.findByUsername(username);
    if (!account.isPresent())
      throw new ResourceNotFoundException("Account cannot be found.");
    return account.get().getId();
  }

}
