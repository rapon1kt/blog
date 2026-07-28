package com.raponi.blog.application.usecase.like;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.domain.model.LikeType;

public interface LikeAndUnlikeUseCase {
  Like handle(String accountId, String targetId, LikeType likeType, LikeTargetType type);
}
