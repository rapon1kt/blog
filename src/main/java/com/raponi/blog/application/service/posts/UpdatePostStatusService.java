package com.raponi.blog.application.service.posts;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.UpdatePostStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class UpdatePostStatusService implements UpdatePostStatusUseCase {

  private final PostRepository postRepository;

  public UpdatePostStatusService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Post handle(String accountId, String postId, String tokenId) {
    Optional<Post> post = this.postRepository.findById(postId);
    if (post.isPresent()) {
      if (post.get().accountId().equals(accountId) && accountId.equals(tokenId)) {
        boolean status = post.get().privateStatus();
        boolean newStatus = status == true ? false : true;
        Post updatedPost = post.get().changeStatus(newStatus);
        this.postRepository.save(updatedPost);
        return updatedPost;
      }
      throw new IllegalArgumentException("Você não tem permissão para fazer isso.");
    }
    throw new IllegalArgumentException("O post em questão não pode ser encontrado.");
  }

}
