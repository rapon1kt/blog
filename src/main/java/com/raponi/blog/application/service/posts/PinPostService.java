package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.post.PinPostUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class PinPostService implements PinPostUseCase {

  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;

  public PinPostService(PostRepository postRepository, AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String postId) {
    boolean isAuthorized = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isAuthorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    Post post = this.postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("This post cannot be found."));
    if (!accountId.equals(post.getAuthorId()))
      throw new AccessDeniedException("You cannot pin a post that is not yours!");

    if (post.isPinned()) {
      post.setPinned(false);
      this.postRepository.save(post);
      return "Post unpinned with success!";
    }

    if (this.postRepository.findByAuthorIdAndPinnedTrue(accountId).size() == 3)
      throw new BusinessRuleException("You can only pin three posts!");
    if (!post.getPostVisibility().equals(PostVisibility.PUBLIC))
      throw new BusinessRuleException("You can only pin public posts!");

    post.setPinned(true);
    this.postRepository.save(post);
    return "Post pinned with success!";
  }

}
