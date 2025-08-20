package com.raponi.blog.application.service.like;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.usecase.like.CountPostLikeUseCase;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;

@Service
public class CountPostLikeService implements CountPostLikeUseCase {

  private final LikeRepository likeRepository;

  public CountPostLikeService(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  @Override
  public long handle(String postId) {
    return this.likeRepository.countByPostId(postId);
  }

}