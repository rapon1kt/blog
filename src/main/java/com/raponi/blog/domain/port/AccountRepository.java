package com.raponi.blog.domain.port;

import java.util.Optional;

import com.raponi.blog.domain.model.Account;

public interface AccountRepository {
  public Account save(Account account);

  public Optional<Account> findById(String id);

  public Optional<Account> findByUsername(String username);

  public boolean existsByEmail(String email);

  public boolean existsByUsername(String username);

  public void deleteById(String id);
}
