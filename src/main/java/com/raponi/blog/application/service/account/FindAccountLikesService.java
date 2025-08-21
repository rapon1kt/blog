package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.usecase.account.FindAccountLikesUseCase;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;

@Service
public class FindAccountLikesService implements FindAccountLikesUseCase {

  private final LikeRepository likeRepository;

  public FindAccountLikesService(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  @Override
  public List<Like> handle(String accountId, String tokenId) {
    if (accountId.isBlank() || accountId.isEmpty()) {
      throw new IllegalArgumentException("O usuário em questão não existe.");
    }

    /* Then, implement option to followers or following accounts to see likes */
    if (accountId.equals(tokenId)) {
      return this.likeRepository.findByAccountId(accountId);
    }

    throw new IllegalArgumentException("Você não tem permissão para ver os likes dessa pessoa.");

  }

}
