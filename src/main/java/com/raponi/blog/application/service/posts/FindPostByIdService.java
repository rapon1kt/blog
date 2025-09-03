package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final PostValidatorService postValidatorService;
  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public FindPostByIdService(PostValidatorService postValidatorService, PostRepository postRepository,
      PostMapper postMapper) {
    this.postValidatorService = postValidatorService;
    this.postRepository = postRepository;
    this.postMapper = postMapper;
  }

  @Override
  public PostResponseDTO handle(String postId) {
    this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = this.postRepository.findById(postId).get();
    PostResponseDTO responsePost = this.postMapper.toResponse(post);
    return responsePost;
  }
}
