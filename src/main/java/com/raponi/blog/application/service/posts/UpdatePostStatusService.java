package com.raponi.blog.application.service.posts;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.UpdatePostStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class UpdatePostStatusService implements UpdatePostStatusUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;
  private final PostMapper postMapper;

  public UpdatePostStatusService(PostRepository postRepository, PostValidatorService postValidatorService,
      AccountValidatorService accountValidatorService, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
    this.postMapper = postMapper;
  }

  @Override
  public PostResponseDTO handle(String accountId, String postId) {
    boolean validatedPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!validatedPost)
      throw new ResourceNotFoundException("This post cannot be found.");
    Post post = this.postRepository.findById(postId).get();
    if (!post.getAccountId().equals(accountId)) {
      throw new ResourceNotFoundException("This post does not belong to this user.");
    }
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (!authorized)
      throw new AccessDeniedException("You don't have permission to do this.");
    post.setPrivateStatus(post.isPrivateStatus() ? false : true);
    post.setModifiedAt(Instant.now());
    this.postRepository.save(post);
    PostResponseDTO responsePost = this.postMapper.toResponse(post);
    return responsePost;
  }

}
