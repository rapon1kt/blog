package com.raponi.blog.domain.usecase.account;

import java.util.List;

import com.raponi.blog.domain.model.Like;

public interface FindAccountLikesUseCase {
  List<Like> handle(String accountId);
}
