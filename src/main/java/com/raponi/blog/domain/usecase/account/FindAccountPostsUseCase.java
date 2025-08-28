package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.domain.model.Post;

public interface FindAccountPostsUseCase {
  public List<Post> handle(String accountId);
}
