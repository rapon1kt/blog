package com.raponi.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import com.raponi.blog.domain.model.Account;

public interface AccountRepository {

  public Account save(Account account);

  public Optional<Account> findById(String id);

  public List<Account> findAll();

  public void deleteById(String id);

  public Optional<Account> findByUsername(String username);

  public boolean existsByUsername(String username);

  public Optional<Account> findByEmail(String email);

  public boolean existsByEmail(String email);

  public Optional<Account> findByEmailOrId(String email, String id);

  public List<Account> findAllByActiveIsTrue();

}
