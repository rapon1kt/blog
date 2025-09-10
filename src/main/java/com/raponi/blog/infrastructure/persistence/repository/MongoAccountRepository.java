package com.raponi.blog.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;

@Repository
public interface MongoAccountRepository extends MongoRepository<AccountEntity, String> {

  public Optional<AccountEntity> findByUsername(String username);

  public boolean existsByUsername(String username);

  public Optional<AccountEntity> findByEmail(String email);

  public boolean existsByEmail(String email);

  @Query("{ '$or': [{ 'email': ?0 }, { '_id': ?1 }]}")
  public Optional<AccountEntity> findByEmailOrId(String email, String id);

  public List<AccountEntity> findAllByActiveIsTrue();

}
