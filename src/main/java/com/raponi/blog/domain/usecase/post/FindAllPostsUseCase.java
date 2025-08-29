package com.raponi.blog.domain.usecase.post;

import java.util.List;

import com.raponi.blog.presentation.protocols.Http;

public interface FindAllPostsUseCase {
  public List<Http.PostResponseBody> handle();
}
