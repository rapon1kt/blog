package com.raponi.blog.domain.port;

import com.raponi.blog.domain.model.Account;

public interface TokenGeneratorService {
  String generateToken(Account account);
}
