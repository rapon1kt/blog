package com.raponi.blog.application.usecase.account;

public record ChangeAccountPasswordCommand(String password, String newPassword) {}
