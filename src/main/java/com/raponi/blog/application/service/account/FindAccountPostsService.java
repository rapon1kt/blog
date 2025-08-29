package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.account.FindAccountPostsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class FindAccountPostsService implements FindAccountPostsUseCase {

  private final PostRepository postRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountPostsService(PostRepository postRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.postRepository = postRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Post> handle(String accountId) {
    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(acc);
    Boolean verifiedAuthority = this.accountValidatorService.verifyAuthority(accountId);

    if (!verifiedAccount)
      throw new AccessDeniedException("Você não tem permissão para fazer isso, reative sua conta.");

    if (verifiedAuthority) {
      return this.postRepository.findByAccountId(accountId);
    }

    return this.postRepository.findByAccountIdAndPrivateStatusFalse(accountId);
  }

}
