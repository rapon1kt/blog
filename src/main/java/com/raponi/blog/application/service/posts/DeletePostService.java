package com.raponi.blog.application.service.posts;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.DeletePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class DeletePostService implements DeletePostUseCase {

  private final PostRepository postRepository;

  public DeletePostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public String handle(String accountId, String postId, String tokenId) {
    Optional<Post> post = this.postRepository.findById(postId);
    if (post.isPresent()) {
      String accountPostId = post.get().accountId();
      if (accountPostId.equals(accountId) && accountPostId.equals(tokenId)) {
        this.postRepository.deleteById(postId);
        return "Post foi deletado com sucesso!";
      }
      throw new IllegalArgumentException("Você não tem permissão para deletar esse post!");
    }
    throw new IllegalArgumentException("O post em questão não existe.");
  }
}
