package com.raponi.blog.application.service.posts;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.post.UpdatePostStatusUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class UpdatePostStatusService implements UpdatePostStatusUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public UpdatePostStatusService(PostRepository postRepository, PostValidatorService postValidatorService,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Post handle(String accountId, String postId, PostVisibility newVisibility) {
    boolean validatedPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!validatedPost)
      throw new ResourceNotFoundException("This post cannot be found.");
    Post post = this.postRepository.findById(postId).get();
    verifyPostPermission(post, accountId);
    post.setPostVisibility(newVisibility);
    if (!PostVisibility.PUBLIC.equals(newVisibility)) {
      post.setPinned(false);
    }
    post.setModifiedAt(Instant.now());
    Post savedPost = this.postRepository.save(post);
    return savedPost;
  }

  private void verifyPostPermission(Post post, String accountId) {
    if (!post.getAuthorId().equals(accountId)) {
      throw new ResourceNotFoundException("This post does not belong to this user.");
    }
    if (!this.accountValidatorService.verifyAccountWithAccountId(accountId))
      throw new AccessDeniedException("You don't have permission to do this.");
  }

}
