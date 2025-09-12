package com.raponi.blog.application.usecase.like;

import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.presentation.dto.LikeResponseDTO;

public interface LikeAndUnlikeUseCase {
  LikeResponseDTO handle(String accountId, String targetId, LikeTargetType type);
}
