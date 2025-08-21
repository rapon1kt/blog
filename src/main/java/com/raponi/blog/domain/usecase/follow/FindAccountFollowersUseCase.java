package com.raponi.blog.domain.usecase.follow;

import java.util.List;

public interface FindAccountFollowersUseCase {
  public List<String> handle(String accountId);
}
