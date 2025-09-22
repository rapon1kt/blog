package com.raponi.blog.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;
import com.raponi.blog.infrastructure.persistence.repository.MongoAccountRepository;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Component
public class AccountRepositoryImpl implements AccountRepository {

  private final MongoAccountRepository mongoRepository;
  private final AccountMapper accountMapper;

  public AccountRepositoryImpl(MongoAccountRepository mongoRepository, AccountMapper accountMapper) {
    this.mongoRepository = mongoRepository;
    this.accountMapper = accountMapper;
  }

  @Override
  public Account save(Account account) {
    AccountEntity accountEntity = this.accountMapper.toEntity(account);
    AccountEntity savedEntity = this.mongoRepository.save(accountEntity);
    return this.accountMapper.toDomain(savedEntity);

  }

  @Override
  public Optional<Account> findById(String id) {
    Optional<AccountEntity> accountEntity = this.mongoRepository.findById(id);
    return Optional.of(accountEntity.map(accountMapper::toDomain).orElse(null));
  }

  @Override
  public List<Account> findAll() {
    return this.mongoRepository.findAll().stream().map(accountMapper::toDomain).toList();
  }

  @Override
  public void deleteById(String id) {
    this.mongoRepository.deleteById(id);
  }

  @Override
  public Optional<Account> findByUsername(String username) {
    Optional<AccountEntity> accountEntity = this.mongoRepository.findByUsername(username);
    return Optional.of(accountEntity.map(accountMapper::toDomain).orElse(null));
  }

  @Override
  public boolean existsByUsername(String username) {
    return this.mongoRepository.existsByUsername(username);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    Optional<AccountEntity> accountEntity = this.mongoRepository.findByEmail(email);
    return Optional.of(accountEntity.map(accountMapper::toDomain).orElse(null));
  }

  @Override
  public boolean existsByEmail(String email) {
    return this.mongoRepository.existsByEmail(email);
  }

  @Override
  public Optional<Account> findByEmailOrId(String email, String id) {
    Optional<AccountEntity> accountEntity = this.mongoRepository.findByEmailOrId(email, id);
    return Optional.of(accountEntity.map(accountMapper::toDomain).orElse(null));
  }

  @Override
  public List<Account> findAllByActiveIsTrue() {
    return this.mongoRepository.findAllByActiveIsTrue().stream().map(accountMapper::toDomain).toList();
  }

}
