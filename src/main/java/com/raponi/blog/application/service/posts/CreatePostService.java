package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.CreatePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.dto.CreatePostRequestDTO;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class CreatePostService implements CreatePostUseCase {

  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostMapper postMapper;

  public CreatePostService(PostRepository postRepository, AccountValidatorService accountValidatorService,
      PostMapper postMapper) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
    this.postMapper = postMapper;
  }

  @Override
  public PostResponseDTO handle(CreatePostRequestDTO requestDTO, String tokenId) {
    Account account = this.accountValidatorService.getAccountByAccountId(tokenId);
    Post post = Post.create(account.getId(), requestDTO.getTitle(), requestDTO.getContent());
    Post savedPost = this.postRepository.save(post);
    PostResponseDTO responsePost = this.postMapper.toResponse(savedPost);
    return responsePost;
  }

}
