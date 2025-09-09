package com.raponi.blog.application.service.posts;

import java.util.List;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.post.FindAllPostsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class FindAllPostsService implements FindAllPostsUseCase {

  private final PostMapper postMapper;

  private final AccountValidatorService accountValidatorService;

  private final PostRepository postRepository;

  public FindAllPostsService(PostRepository postRepository, AccountValidatorService accountValidatorService,
      PostMapper postMapper) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
    this.postMapper = postMapper;
  }

  @Override
  public List<PostResponseDTO> handle() {
    if (this.accountValidatorService.isAdmin())
      return this.postRepository.findAll().stream().map(postMapper::toResponse).toList();
    else
      return this.postRepository.findByPrivateStatus(false).stream().map(postMapper::toResponse).toList();
  };

}
