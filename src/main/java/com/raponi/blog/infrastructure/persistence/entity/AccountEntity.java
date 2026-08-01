package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private boolean banned;
  private String role;
  private Instant createdAt;
  private Instant modifiedAt;
}
