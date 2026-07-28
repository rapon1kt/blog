package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.post.CreatePostCommand;
import com.raponi.blog.application.usecase.post.CreatePostUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.PostRepository;

@Service
public class CreatePostService implements CreatePostUseCase {

  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;

  public CreatePostService(PostRepository postRepository, AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Post handle(CreatePostCommand command, String tokenId) {
    validateAccount(tokenId);
    Post post = Post.create(tokenId, command.title(), command.content());
    Post savedPost = this.postRepository.save(post);
    return savedPost;
  }

  private void validateAccount(String tokenId) {
    if (!accountValidatorService.verifyAccountWithAccountId(tokenId))
      throw new AccessDeniedException("You don't have permission to do this.");
  }

}
