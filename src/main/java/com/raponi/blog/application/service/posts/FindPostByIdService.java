package com.raponi.blog.application.service.posts;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.FindPostByIdUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class FindPostByIdService implements FindPostByIdUseCase {

  private final PostRepository postRepository;

  public FindPostByIdService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Post handle(String postId, String accountId) {
    Optional<Post> post = this.postRepository.findById(postId);
    if (post.isPresent()) {
      if (post.get().privateStatus()) {
        if (post.get().accountId().equals(accountId)) {
          return this.postRepository.findById(postId).get();
        }
        throw new IllegalArgumentException("Você não tem permissão para visualizar esse post.");
      }
      return this.postRepository.findById(postId).get();
    }
    throw new IllegalArgumentException("Post não encontrado ou não existe.");
  }

}
