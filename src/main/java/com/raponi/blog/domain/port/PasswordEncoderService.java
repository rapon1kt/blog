package com.raponi.blog.domain.port;

public interface PasswordEncoderService {
  public String encode(String rawPassword);

  public boolean matches(String rawPassword, String encodedPassword);
}
