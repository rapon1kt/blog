package com.raponi.blog.application.usecase.follow;

import java.util.List;

public interface FindAccountFollowersUseCase {
  public List<String> handle(String accountId);
}
