package com.raponi.blog.application.usecase;

import com.raponi.blog.application.command.SignInAccountCommand;
import com.raponi.blog.application.result.SignInAccountResult;

public interface SignInAccountUseCase {

  public SignInAccountResult handle(SignInAccountCommand command);

}
