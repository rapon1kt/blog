package com.raponi.blog.application.service.account;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountPostsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.domain.repository.AccountRepository;
import com.raponi.blog.domain.repository.PostRepository;
import com.raponi.blog.presentation.dto.PostResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.mapper.PostMapper;

@Service
public class FindAccountPostsService implements FindAccountPostsUseCase {

  private final PostRepository postRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;
  private final PostMapper postMapper;

  public FindAccountPostsService(PostRepository postRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService, PostMapper postMapper) {
    this.postRepository = postRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
    this.postMapper = postMapper;
  }

  @Override
  public List<PostResponseDTO> handle(String username) {
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive("username", username);
    if (!verifiedAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Boolean verifiedAuthority = this.accountValidatorService.verifyAuthority("username", username);
    Account acc = this.accountRepository.findByUsername(username).get();
    boolean isViwerBlocked = this.accountValidatorService.isBlocked(acc.getId());
    if (isViwerBlocked)
      return null;
    return this.getAccountFeed(verifiedAuthority, acc.getId());
  }

  private List<PostResponseDTO> getAccountFeed(boolean verifiedAuthority, String accountId) {
    List<Post> posts = new ArrayList<>();
    List<Post> pinned = new ArrayList<>();
    if (verifiedAuthority) {
      this.postRepository.findByAuthorId(accountId).forEach(post -> {
        if (post.isPinned()) {
          pinned.add(post);
        } else {
          posts.add(post);
        }
      });
      posts.addAll(pinned);
      return posts.stream().map(postMapper::toResponse).toList();
    }
    this.postRepository.findByPostVisibility(PostVisibility.PUBLIC).forEach(post -> {
      if (post.isPinned()) {
        pinned.add(post);
      } else {
        posts.add(post);
      }
    });
    posts.addAll(pinned);
    posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
    posts.sort(Comparator.comparing(Post::isPinned).reversed());
    return posts.stream().map(postMapper::toResponse).toList();
  }
}
