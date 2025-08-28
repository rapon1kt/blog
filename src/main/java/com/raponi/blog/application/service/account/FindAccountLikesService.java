package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.usecase.account.FindAccountLikesUseCase;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;

@Service
public class FindAccountLikesService implements FindAccountLikesUseCase {

  private final LikeRepository likeRepository;
  private final AccountValidatorService accountValidatorService;

  public FindAccountLikesService(LikeRepository likeRepository, AccountValidatorService accountValidatorService) {
    this.likeRepository = likeRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Like> handle(String accountId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String tokenId = auth.getName();
    String role = auth.getAuthorities().iterator().next().getAuthority();
    Account account = this.accountValidatorService.getAccountByAccountId(accountId, tokenId, role);
    return this.likeRepository.findByAccountId(account.id());
  }

}
