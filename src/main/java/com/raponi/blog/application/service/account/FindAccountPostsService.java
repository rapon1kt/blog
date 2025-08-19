package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.account.FindAccountPostsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class FindAccountPostsService implements FindAccountPostsUseCase {

  private final PostRepository postRepository;

  public FindAccountPostsService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public List<Post> handle(String accountId) {
    return this.postRepository.findByAccountId(accountId);
  }

}
