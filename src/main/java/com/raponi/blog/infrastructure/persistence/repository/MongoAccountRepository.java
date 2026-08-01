package com.raponi.blog.infrastructure.persistence.repository;

import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAccountRepository extends MongoRepository<AccountEntity, String> {
  public Optional<AccountEntity> findByUsername(String username);

  public boolean existsByUsername(String username);

  public Optional<AccountEntity> findByEmail(String email);

  public boolean existsByEmail(String email);
}
