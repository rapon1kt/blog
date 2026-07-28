package com.raponi.blog.domain.exception;

public class LikableNotFoundException extends RuntimeException {

  public LikableNotFoundException(String message) {
    super(message);
  }
}
