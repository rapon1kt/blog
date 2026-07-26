package com.raponi.blog.application.usecase.account;

public record CreateAccountCommand(String username, String email, String password) {}
