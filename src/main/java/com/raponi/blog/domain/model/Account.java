package com.raponi.blog.domain.model;

import java.time.Instant;

public class Account {
  private String id;
  private String email;
  private String username;
  private String picture;
  private String description;
  private String password;
  private boolean active;
  private String role;
  private Instant createdAt;
  private Instant modifiedAt;

  public Account(String id, String email, String username, String picture, String description, String password,
      boolean active, String role, Instant createdAt, Instant modifiedAt) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.picture = picture;
    this.description = description;
    this.password = password;
    this.active = active;
    this.role = role;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Account create(String email, String username, String password) {
    return new Account(null, email, username, null, null, password, true, "USER", Instant.now(), Instant.now());
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

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
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
