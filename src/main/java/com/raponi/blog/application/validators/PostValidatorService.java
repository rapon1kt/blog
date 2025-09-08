package com.raponi.blog.application.validators;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.PostValidatorUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class PostValidatorService implements PostValidatorUseCase {

  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;

  public PostValidatorService(PostRepository postRepository,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public boolean validatePostPresenceAndPrivate(String postId) {
    Optional<Post> post = this.postRepository.findById(postId);
    if (post.isPresent()) {
      if (post.get().isPrivateStatus()) {
        if (this.accountValidatorService.verifyAuthority(post.get().getAccountId())) {
          return true;
        }
        return false;
      }
      return true;
    }
    return false;
  }
}