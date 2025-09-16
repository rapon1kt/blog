package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountPostsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
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
    Optional<Account> acc = this.accountRepository.findByUsername(username);
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(acc);
    Boolean verifiedAuthority = this.accountValidatorService.verifyAuthority(acc.get().getId());

    if (!verifiedAccount)
      throw new AccessDeniedException("You don't have permission to do this.");

    if (verifiedAuthority) {
      return this.postRepository.findByAccountId(acc.get().getId()).stream().map(postMapper::toResponse).toList();
    }

    return this.postRepository.findByAccountIdAndPostVisibility(acc.get().getId(), PostVisibility.PUBLIC).stream()
        .map(postMapper::toResponse)
        .toList();
  }

}
