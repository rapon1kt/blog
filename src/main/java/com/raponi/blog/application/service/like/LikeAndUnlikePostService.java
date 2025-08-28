package com.raponi.blog.application.service.like;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.usecase.like.LikeAndUnlikePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.AccountRepository;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;

@Service
public class LikeAndUnlikePostService implements LikeAndUnlikePostUseCase {

  private final LikeRepository likeRepository;
  private final AccountRepository accountRepository;
  private final AccountValidatorService accountValidatorService;

  public LikeAndUnlikePostService(LikeRepository likeRepository, AccountRepository accountRepository,
      AccountValidatorService accountValidatorService) {
    this.likeRepository = likeRepository;
    this.accountRepository = accountRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String postId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    String accountId = auth.getName();

    Optional<Account> acc = this.accountRepository.findById(accountId);
    Account validatedAcc = this.accountValidatorService.verifyPresenceAndActive(acc, role);

    if (this.likeRepository.existsByPostIdAndAccountId(postId, validatedAcc.id())) {
      this.likeRepository.deleteByPostIdAndAccountId(postId, validatedAcc.id());
      return "Unliked!";
    }
    Like like = Like.create(validatedAcc.id(), postId);
    this.likeRepository.save(like);
    return "Liked!";
  };

}
