package com.raponi.blog.application.usecase.like;

import java.util.List;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.model.LikeTargetType;

public interface FindTargetLikesUseCase {
  List<Like> handle(String targetId, LikeTargetType type);
}
