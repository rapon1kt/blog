package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final PostValidatorService postValidatorService;
  private final PostRepository postRepository;

  public FindPostByIdService(PostValidatorService postValidatorService, PostRepository postRepository) {
    this.postValidatorService = postValidatorService;
    this.postRepository = postRepository;
  }

  @Override
  public Http.PostResponseBody handle(String postId) {
    Boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = isValidPost ? this.postRepository.findById(postId).get() : null;
    return post.toResponseBody();
  }
}
