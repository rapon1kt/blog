package com.raponi.blog.domain.exception;

public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException(String message) {
    super(message);
  }
}
