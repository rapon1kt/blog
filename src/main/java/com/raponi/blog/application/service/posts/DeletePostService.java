package com.raponi.blog.application.service.posts;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.DeletePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class DeletePostService implements DeletePostUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public DeletePostService(PostRepository postRepository, PostValidatorService postValidatorService,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String postId) {
    this.postValidatorService.validatePostPresenceAndPrivate(postId);
    Post post = this.postRepository.findById(postId).get();
    if (!post.accountId().equals(accountId)) {
      throw new IllegalArgumentException("Esse usuário não é o autor desse post!");
    }
    boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (authorized) {
      this.postRepository.deleteById(post.id());
      return "Post foi deletado com sucesso!";
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }
}
