package com.raponi.blog.application.usecase.account;

import java.util.List;

import com.raponi.blog.domain.model.Like;

public interface FindAccountLikesUseCase {
  List<Like> handle(String accountId);
}
