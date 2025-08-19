package com.raponi.blog.application.service.posts;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.CreatePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class CreatePostService implements CreatePostUseCase {

  private final PostRepository postRepository;

  public CreatePostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Post handle(Post newPost, String tokenId) {
    Post post = Post.create(tokenId, newPost.title(), newPost.content());
    this.postRepository.save(post);
    return post;
  }

}
