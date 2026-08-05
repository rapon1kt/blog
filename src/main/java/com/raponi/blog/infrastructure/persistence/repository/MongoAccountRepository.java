package com.raponi.blog.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.raponi.blog.infrastructure.persistence.document.AccountDocument;

public interface MongoAccountRepository extends MongoRepository<AccountDocument, String> {

  public Optional<AccountDocument> findByUsername(String username);

  public boolean existsByUsername(String username);

  public boolean existsByEmail(String email);

}
