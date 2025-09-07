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
  private String picture;
  private String description;
  private String password;
  private boolean active = true;
  private String role;
  private Instant createdAt;
  private Instant modifiedAt;

  public AccountEntity(Account account) {
    this.id = account.getId();
    this.username = account.getUsername();
    this.picture = account.getPicture();
    this.description = account.getDescription();
    this.email = account.getEmail();
    this.password = account.getPassword();
    this.active = account.isActive();
    this.role = account.getRole();
    this.createdAt = account.getCreatedAt();
    this.modifiedAt = account.getModifiedAt();
  }

}
