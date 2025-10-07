package com.raponi.blog.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.BanValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Ban;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.BanRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class AppAccountServiceImpl implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;
  private BanRepository banRepository;
  private BanValidatorService banValidatorService;

  public AppAccountServiceImpl(BanRepository banRepository, BanValidatorService banValidatorService) {
    this.banRepository = banRepository;
    this.banValidatorService = banValidatorService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = this.accountRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found!"));

    if (account.isBanned() && !banValidatorService.isBanValid(account.getId())) {
      Ban activeBan = this.banRepository.findTopByBannedIdOrderByBannedAtDesc(account.getId()).get();
      throw new AccessDeniedException(
          "Your account is temporarily banned until " + activeBan.getExpiresAt().toString()
              + ". Reason: " + activeBan.getReason() + " - " + activeBan.getModeratorDescription());
    }
    return User.builder()
        .username(account.getUsername())
        .password(account.getPassword())
        .build();
  }

  public String getAccountIdByUsername(String username) {
    Account account = this.accountRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found!"));
    return account.getId();
  }

}
