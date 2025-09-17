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
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class AccountValidatorService implements AccountValidatorUseCase {

  private final MongoTemplate mongoTemplate;

  public AccountValidatorService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
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
    Account account = Optional.of(this.mongoTemplate.findOne(query, Account.class))
        .orElseThrow(() -> new ResourceNotFoundException("This account cannot be found."));
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
    Optional<Account> acc = Optional.of(this.mongoTemplate.findOne(query, Account.class));
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

}
