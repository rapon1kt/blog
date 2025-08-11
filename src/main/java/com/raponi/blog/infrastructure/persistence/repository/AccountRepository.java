package com.raponi.blog.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

  public Optional<Account> findByUsername(String username);

  public Optional<Account> findByEmail(String email);

}
