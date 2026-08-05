package com.raponi.blog.domain.model;

import java.time.Instant;

public class Account {

  private String id;
  private String email;
  private String username;
  private String password;
  private String avatarUrl;
  private AccountRole role;
  private Instant createdAt;
  private Instant modifiedAt;

  public Account() {
  }

  public Account(String email, String username, String passwordHash) {
    this.email = email;
    this.username = username;
    this.password = passwordHash;
    this.role = AccountRole.USER;
    this.createdAt = Instant.now();
    this.modifiedAt = Instant.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public AccountRole getRole() {
    return role;
  }

  public void setRole(AccountRole role) {
    this.role = role;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

}
