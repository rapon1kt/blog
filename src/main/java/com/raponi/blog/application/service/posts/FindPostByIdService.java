package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public FindPostByIdService(AccountValidatorService accountValidatorService, PostValidatorService postValidatorService,
      PostRepository postRepository) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Post handle(String postId) {
    boolean verifiedPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!verifiedPost)
      throw new ResourceNotFoundException("This post cannot be found.");

    Post post = this.postRepository.findById(postId).get();

    if (this.accountValidatorService.isAdmin()) {
      return post;
    }

    if (!this.accountValidatorService.isBlocked(post.getAuthorId())
        && !this.accountValidatorService.isBanned(post.getAuthorId()))
      return post;

    return null;

  }
}
