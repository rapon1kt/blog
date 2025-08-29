package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.CreatePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class CreatePostService implements CreatePostUseCase {

  private final PostRepository postRepository;

  public CreatePostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Http.PostResponseBody handle(Post newPost, String tokenId) {
    Post post = Post.create(tokenId, newPost.title(), newPost.content());
    this.postRepository.save(post);
    return post.toResponseBody();
  }

}
