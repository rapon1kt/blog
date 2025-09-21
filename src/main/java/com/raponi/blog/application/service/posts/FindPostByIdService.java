package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final AccountValidatorService accountValidatorService;
  private final PostValidatorService postValidatorService;
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public FindPostByIdService(AccountValidatorService accountValidatorService, PostValidatorService postValidatorService,
      PostRepository postRepository,
      PostMapper postMapper) {
    this.accountValidatorService = accountValidatorService;
    this.postValidatorService = postValidatorService;
    this.postRepository = postRepository;
    this.postMapper = postMapper;
  }

  @Override
  public PostResponseDTO handle(String postId) {
    boolean verifiedPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!verifiedPost)
      throw new ResourceNotFoundException("This post cannot be found.");

    Post post = this.postRepository.findById(postId).get();
    PostResponseDTO responsePost = this.postMapper.toResponse(post);
    if (this.accountValidatorService.isAdmin()) {
      return responsePost;
    } else {
      if (this.accountValidatorService.isBlocked(post.getAuthorId()))
        return null;
      return responsePost;
    }
  }
}
