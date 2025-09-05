package com.raponi.blog.application.validators;

public record ImageValidationResponse(
    boolean isValid,
    String message) {

}
