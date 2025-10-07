package com.raponi.blog.application.validators;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.AccountValidatorUseCase;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;
import com.raponi.blog.infrastructure.persistence.entity.BlockEntity;
import com.raponi.blog.presentation.mapper.AccountMapper;

@Service
public class AccountValidatorService implements AccountValidatorUseCase {

  private final MongoTemplate mongoTemplate;
  private final AccountMapper accountMapper;
  private final AccountRepository accountRepository;

  public AccountValidatorService(MongoTemplate mongoTemplate, AccountMapper accountMapper,
      AccountRepository accountRepository) {
    this.mongoTemplate = mongoTemplate;
    this.accountMapper = accountMapper;
    this.accountRepository = accountRepository;
  }

  private Authentication getAuth() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean verifyAccountWithAccountId(String accountId) {
    Boolean verifiedAccount = verifyPresenceAndActive("_id", accountId);
    if (verifiedAccount) {
      Boolean authorized = verifyAuthority("_id", accountId);
      if (!authorized)
        return false;

      return true;
    }
    return false;
  }

  public boolean verifyAccountWithEmail(String email) {
    Boolean verifiedAccount = verifyPresenceAndActive("email", email);
    if (verifiedAccount) {
      Boolean authorized = verifyAuthority("email", email);
      if (!authorized)
        return false;

      return true;
    }
    return false;
  }

  public boolean verifyAccountWithUsername(String username) {
    Boolean verifiedAccount = verifyPresenceAndActive("username", username);
    if (verifiedAccount) {
      Boolean authorized = verifyAuthority("username", username);
      if (!authorized)
        return false;

      return true;
    }
    return false;
  }

  public boolean isAdmin() {
    String role = this.getAuth().getAuthorities().iterator().next().getAuthority();
    return role.equals("ROLE_ADMIN");
  }

  public boolean verifyAuthority(String key, String value) {
    Query query = new Query(Criteria.where(key).is(value));
    Account account = accountMapper.toDomain(mongoTemplate.findOne(query, AccountEntity.class));
    if (!account.getId().equals(this.getAuth().getName())) {
      if (!isAdmin()) {
        return false;
      }
      return true;
    }
    return true;
  }

  public boolean verifyPresenceAndActive(String key, String value) {
    Query query = new Query(Criteria.where(key).is(value));
    Optional<Account> acc = Optional
        .of(accountMapper.toDomain(mongoTemplate.findOne(query, AccountEntity.class)));
    if (acc.isPresent()) {
      if (isAdmin()) {
        return true;
      }
      if (!acc.get().isActive()) {
        return false;
      }
      return true;
    }
    return false;
  }

  public boolean isBlocked(String accountId) {
    if (!this.getAuth().isAuthenticated())
      return false;
    Query query = new Query(Criteria.where("blockedId").is(getAuth().getName()).and("blockerId").is(accountId));
    boolean isViwerBlocked = this.mongoTemplate.exists(query, BlockEntity.class);
    return isViwerBlocked;
  }

  public boolean isBanned(String accountId) {
    boolean verifiedAccount = this.verifyPresenceAndActive("_id", accountId);
    if (verifiedAccount) {
      return this.accountRepository.findById(accountId).get().isBanned();
    }
    return false;
  }

  public String verifyAccountWithUsernameAndReturnId(String username) {
    if (verifyPresenceAndActive("username", username)) {
      Query query = new Query(Criteria.where("username").is(username));
      Account account = accountMapper.toDomain(mongoTemplate.findOne(query, AccountEntity.class));
      return account.getId();
    }
    return null;
  }

}
