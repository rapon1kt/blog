package com.raponi.blog.application.usecase.account;

import java.util.List;

import com.raponi.blog.domain.model.Post;

public interface FindAccountPostsUseCase {
  public List<Post> handle(String username);
}
