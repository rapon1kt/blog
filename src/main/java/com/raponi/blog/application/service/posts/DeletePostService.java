package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.post.DeletePostUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.InvalidParamException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class DeletePostService implements DeletePostUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public DeletePostService(PostRepository postRepository, PostValidatorService postValidatorService,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String postId) {
    boolean validatedPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!validatedPost) {
      throw new ResourceNotFoundException("This post cannot be found.");
    }
    Post post = this.postRepository.findById(postId).get();
    if (!post.getAccountId().equals(accountId)) {
      throw new InvalidParamException("This post does not belong to this user.");
    }
    boolean authorized = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!authorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    this.postRepository.deleteById(post.getId());
    return "Post deleted with success!";
  }
}
