package com.raponi.blog.domain.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "accounts")
public class Account {
  @Id
  private String id;
  private String email;
  private String username;
  private String password;

  private Instant createdAt;
  private Instant modifiedAt;
}
