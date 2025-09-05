package com.raponi.blog.application.service.like;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.usecase.like.CountPostLikeUseCase;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;
import com.raponi.blog.presentation.errors.InternalServerException;

@Service
public class CountPostLikeService implements CountPostLikeUseCase {

  private final LikeRepository likeRepository;
  private final PostValidatorService postValidatorService;

  public CountPostLikeService(LikeRepository likeRepository, PostValidatorService postValidatorService) {
    this.likeRepository = likeRepository;
    this.postValidatorService = postValidatorService;
  }

  @Override
  public long handle(String postId) {
    Boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(postId);
    if (!isValidPost)
      throw new InternalServerException("Something went wrong.");
    return this.likeRepository.countByPostId(postId);
  }

}