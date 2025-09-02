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
import com.raponi.blog.presentation.errors.AccessDeniedException;

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
    String accountId = auth.getName();

    Optional<Account> acc = this.accountRepository.findById(accountId);
    Boolean verifiedAccount = this.accountValidatorService.verifyPresenceAndActive(acc);

    if (!verifiedAccount)
      throw new AccessDeniedException("You must active your account to do this.");

    if (this.likeRepository.existsByPostIdAndAccountId(postId, acc.get().id())) {
      this.likeRepository.deleteByPostIdAndAccountId(postId, acc.get().id());
      return "Unliked!";
    }
    Like like = Like.create(acc.get().id(), postId);
    this.likeRepository.save(like);
    return "Liked!";
  };

}
