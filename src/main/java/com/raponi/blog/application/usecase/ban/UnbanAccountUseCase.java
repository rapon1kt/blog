package com.raponi.blog.application.usecase.ban;

public interface UnbanAccountUseCase {

  String handle(String moderatorId, String bannedId);

}
