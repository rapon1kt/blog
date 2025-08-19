package com.raponi.blog.application.service.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.usecase.account.FindAccountPostsUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.PostRepository;

@Service
public class FindAccountPostsService implements FindAccountPostsUseCase {

  private final PostRepository postRepository;
  private final AccountRepository accountRepository;

  public FindAccountPostsService(PostRepository postRepository, AccountRepository accountRepository) {
    this.postRepository = postRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Post> handle(String accountId, String currentAccountId) {
    Optional<Account> account = this.accountRepository.findById(accountId);
    if (account.isPresent()) {
      if (accountId.equals(currentAccountId)) {
        return this.postRepository.findByAccountId(accountId);
      }
      return this.postRepository.findByAccountIdAndPrivateStatusFalse(accountId);
    }
    throw new IllegalArgumentException("O usuário em questão não existe.");
  }

}
