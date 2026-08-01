package com.raponi.blog.infrastructure.repository;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;
import com.raponi.blog.infrastructure.persistence.mapper.AccountInfraMapper;
import com.raponi.blog.infrastructure.persistence.repository.MongoAccountRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryImpl implements AccountRepository {

  private final MongoAccountRepository mongoRepository;
  private final AccountInfraMapper accountMapper;

  public AccountRepositoryImpl(MongoAccountRepository mongoRepository, AccountInfraMapper accountMapper) {
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
}
