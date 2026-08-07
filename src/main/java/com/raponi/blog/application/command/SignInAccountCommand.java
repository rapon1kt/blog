package com.raponi.blog.application.command;

public record SignInAccountCommand(
    String email,
    String password) {

}
