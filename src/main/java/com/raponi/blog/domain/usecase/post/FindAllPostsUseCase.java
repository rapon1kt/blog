package com.raponi.blog.domain.usecase.post;

import java.util.List;

import com.raponi.blog.domain.model.Post;

public interface FindAllPostsUseCase {
  public List<Post> handle();
}
