package com.raponi.blog.presentation.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.raponi.blog.presentation.errors.NotFoundError;
import com.raponi.blog.presentation.errors.ServerError;

public class HttpHelper {

  public static ResponseEntity<Object> badRequest(Exception error) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
  }

  public static ResponseEntity<Object> serverError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServerError());
  }

  public static ResponseEntity<Object> notFound(String message) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundError(message).getMessage());
  }

  public static <T> ResponseEntity<T> ok(T data) {
    return ResponseEntity.status(HttpStatus.OK).body(data);
  }

}
