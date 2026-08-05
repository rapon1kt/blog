package com.raponi.blog.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.port.AccountRepository;
import com.raponi.blog.infrastructure.persistence.document.AccountDocument;
import com.raponi.blog.infrastructure.persistence.mapper.AccountInfraMapper;

@Component
public class AccountRepositoryImpl implements AccountRepository {

  private final AccountInfraMapper mapper;
  private final MongoAccountRepository mongoAccountRepository;

  public AccountRepositoryImpl(AccountInfraMapper mapper, MongoAccountRepository mongoAccountRepository) {
    this.mapper = mapper;
    this.mongoAccountRepository = mongoAccountRepository;
  }

  @Override
  public Account save(Account Account) {
    AccountDocument document = mapper.toDocument(Account);
    AccountDocument savedDocument = mongoAccountRepository.save(document);
    return mapper.toDomain(savedDocument);
  }

  @Override
  public Optional<Account> findById(String id) {
    return mongoAccountRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Account> findByUsername(String username) {
    return mongoAccountRepository.findByUsername(username).map(mapper::toDomain);
  }

  @Override
  public boolean existsByEmail(String email) {
    return mongoAccountRepository.existsByEmail(email);
  }

  @Override
  public boolean existsByUsername(String username) {
    return mongoAccountRepository.existsByUsername(username);
  }

  @Override
  public void deleteById(String id) {
    mongoAccountRepository.deleteById(id);
  }

}
