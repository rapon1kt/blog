package com.raponi.blog.domain.port;

import com.raponi.blog.domain.model.Account;

public interface TokenGeneratorPort {
  public String generateToken(Account account);
}
