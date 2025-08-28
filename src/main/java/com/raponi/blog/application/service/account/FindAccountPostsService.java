package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    String tokenId = auth.getName();
    Optional<Account> acc = this.accountRepository.findById(accountId);
    this.accountValidatorService.verifyPresenceAndActive(acc, role);
    if (this.accountValidatorService.verifyAuthority(accountId, tokenId, role)) {
      return this.postRepository.findByAccountId(accountId);
    }
    return this.postRepository.findByAccountIdAndPrivateStatusFalse(accountId);
  }

}
