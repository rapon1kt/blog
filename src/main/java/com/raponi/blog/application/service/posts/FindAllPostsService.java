package com.raponi.blog.application.service.posts;

import java.util.List;
import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.FindAllPostsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class FindAllPostsService implements FindAllPostsUseCase {

  private final PostRepository postRepository;

  public FindAllPostsService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public List<Http.PostResponseBody> handle() {
    return this.postRepository.findByPrivateStatus(false).stream().map(Post::toResponseBody).toList();
  };

}
