package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.UpdatePostStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.protocols.Http;

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
  public Http.PostResponseBody handle(String accountId, String postId) {
    this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = this.postRepository.findById(postId).get();
    if (!post.accountId().equals(accountId)) {
      throw new ResourceNotFoundException("This post does not belong to this user.");
    }
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (!authorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    boolean newStatus = post.privateStatus() == true ? false : true;
    Post updatedPost = post.changeStatus(newStatus);
    this.postRepository.save(updatedPost);
    return updatedPost.toResponseBody();
  }

}
