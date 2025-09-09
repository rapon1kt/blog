package com.raponi.blog.application.usecase.follow;

import java.util.List;

public interface FindAccountFollowingUseCase {
  public List<String> handle(String accountId);
}
