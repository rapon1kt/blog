package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.CreatePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class CreatePostService implements CreatePostUseCase {

  private final PostRepository postRepository;
  private final AccountValidatorService accountValidatorService;

  public CreatePostService(PostRepository postRepository, AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.PostResponseBody handle(Post newPost, String tokenId) {
    Account account = this.accountValidatorService.getAccountByAccountId(tokenId);
    Post post = Post.create(account.id(), newPost.title(), newPost.content());
    this.postRepository.save(post);
    return post.toResponseBody();
  }

}
