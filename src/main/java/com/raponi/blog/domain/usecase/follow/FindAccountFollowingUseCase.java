package com.raponi.blog.domain.usecase.follow;

import java.util.List;

public interface FindAccountFollowingUseCase {
  public List<String> handle(String accountId);
}
