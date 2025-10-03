package com.raponi.blog.application.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

  public AppAccountServiceImpl(BanRepository banRepository) {
    this.banRepository = banRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = this.accountRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found!"));

    Optional<Ban> optionalBan = this.banRepository.findByBannedIdAndActiveTrue(account.getId());

    if (optionalBan.isPresent()) {
      Ban activeBan = optionalBan.get();
      if (activeBan.getExpiresAt().isAfter(Instant.now())) {
        throw new AccessDeniedException(
            "Your account is temporarily banned until " + activeBan.getExpiresAt().toString()
                + ". Reason: " + activeBan.getReason() + " - " + activeBan.getModeratorDescription());
      } else {
        activeBan.setActive(false);
        this.banRepository.save(activeBan);
      }
    }
    return User.builder()
        .username(account.getUsername())
        .password(account.getPassword())
        .build();
  }

  public String getAccountIdByUsername(String username) {
    Account account = this.accountRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found!"));
    Optional<Ban> optionalBan = this.banRepository.findByBannedIdAndActiveTrue(account.getId());
    if (optionalBan.isPresent()) {
      Ban activeBan = optionalBan.get();
      if (activeBan.getExpiresAt().isAfter(Instant.now())) {
        throw new AccessDeniedException("Your account is temporarily banned until" + activeBan.getExpiresAt().toString()
            + ". Reason: " + activeBan.getReason() + " - " + activeBan.getModeratorDescription());
      } else {
        activeBan.setActive(false);
        this.banRepository.save(activeBan);
      }
    }
    return account.getId();
  }

}
