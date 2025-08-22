package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Account;

import lombok.Data;

@Data
@Document(collection = "accounts")
public class AccountEntity {
  @Id
  private String id;
  private String email;
  private String username;
  private String password;
  private boolean active = true;
  private String role;
  private Instant createdAt;
  private Instant modifiedAt;

  public AccountEntity(Account account) {
    this.id = account.id();
    this.username = account.username();
    this.email = account.email();
    this.password = account.password();
    this.active = account.active();
    this.role = account.role();
    this.createdAt = account.createdAt();
    this.modifiedAt = account.modifiedAt();
  }

  public Account toDomain() {
    return new Account(id, username, email, password, active, role, createdAt, modifiedAt);
  }

}
