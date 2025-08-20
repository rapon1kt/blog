package com.raponi.blog.application.service.like;

import org.springframework.stereotype.Service;

import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.usecase.like.LikeAndUnlikePostUseCase;
import com.raponi.blog.infrastructure.persistence.repository.LikeRepository;

@Service
public class LikeAndUnlikePostService implements LikeAndUnlikePostUseCase {

  private final LikeRepository likeRepository;

  public LikeAndUnlikePostService(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  @Override
  public String handle(String postId, String accountId) {
    if (this.likeRepository.existsByPostIdAndAccountId(postId, accountId)) {
      this.likeRepository.deleteByPostIdAndAccountId(postId, accountId);
      return "Liked!";
    }
    Like like = Like.create(accountId, postId);
    this.likeRepository.save(like);
    return "Unliked!";
  };

}
