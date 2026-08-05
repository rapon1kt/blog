package com.raponi.blog.application.command;

public record SignUpAccountCommand(
    String email,
    String username,
    String password) {

}
