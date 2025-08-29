package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final PostValidatorService postValidatorService;

  public FindPostByIdService(PostValidatorService postValidatorService) {
    this.postValidatorService = postValidatorService;
  }

  @Override
  public Http.PostResponseBody handle(String postId) {
    Post post = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    return post.toResponseBody();
  }
}
