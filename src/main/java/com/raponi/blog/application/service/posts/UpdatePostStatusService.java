package com.raponi.blog.application.service.posts;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.application.service.PostValidatorService;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.post.UpdatePostStatusUseCase;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;
import com.raponi.blog.presentation.protocols.Http;

@Service
public class UpdatePostStatusService implements UpdatePostStatusUseCase {

  private final PostRepository postRepository;
  private final PostValidatorService postValidatorService;
  private final AccountValidatorService accountValidatorService;

  public UpdatePostStatusService(PostRepository postRepository, PostValidatorService postValidatorService,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.postValidatorService = postValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Http.PostResponseBody handle(String accountId, String postId) {
    Post post = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!post.accountId().equals(accountId)) {
      throw new IllegalArgumentException("Esse usuário não é o autor desse post!");
    }
    Boolean authorized = this.accountValidatorService.verifyAuthority(accountId);
    if (authorized) {
      boolean newStatus = post.privateStatus() == true ? false : true;
      Post updatedPost = post.changeStatus(newStatus);
      this.postRepository.save(updatedPost);
      return updatedPost.toResponseBody();
    }
    throw new AccessDeniedException("Você não tem permissão para fazer isso.");
  }

}
