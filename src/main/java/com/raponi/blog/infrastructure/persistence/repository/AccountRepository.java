package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

  public Optional<Account> findByUsername(String username);

  boolean existsByUsername(String username);

  public Optional<Account> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query("{ '$or': [{ 'email': ?0 }, { '_id': ?1 }]}")
  public Optional<Account> findByEmailOrId(String email, String id);

  public List<Account> findAllByActiveIsTrue();

}
