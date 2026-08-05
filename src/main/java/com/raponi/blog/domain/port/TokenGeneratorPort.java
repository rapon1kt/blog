package com.raponi.blog.domain.port;

public interface TokenGeneratorPort {
  public String generateToken(String accountId);
}
