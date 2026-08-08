package com.raponi.blog.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.raponi.blog.domain.exception.InvalidCredentialsException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(exception = InvalidCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse invalidCredentialsException(InvalidCredentialsException ex) {
    return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

}
