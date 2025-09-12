package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.FindAccountLikesUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.repository.LikeRepository;

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
    Account account = this.accountValidatorService.getAccountByAccountId(accountId);
    return this.likeRepository.findByAccountId(account.getId());
  }

}
