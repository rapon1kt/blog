package com.raponi.blog.domain.repository;

import com.raponi.blog.domain.model.Account;
import java.util.Optional;

public interface AccountRepository {
  public Account save(Account account);

  public Optional<Account> findById(String id);

  public void deleteById(String id);

  public Optional<Account> findByUsername(String username);

  public boolean existsByUsername(String username);

  public Optional<Account> findByEmail(String email);

  public boolean existsByEmail(String email);
}
