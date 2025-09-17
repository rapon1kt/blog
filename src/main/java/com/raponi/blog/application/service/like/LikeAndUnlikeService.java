package com.raponi.blog.application.service.like;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.like.LikeAndUnlikeUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.*;
import com.raponi.blog.domain.repository.*;
import com.raponi.blog.presentation.dto.LikeResponseDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class LikeAndUnlikeService implements LikeAndUnlikeUseCase {

  private final LikeRepository likeRepository;
  private final LikableRepository likableRepository;
  private final AccountValidatorService accountValidatorService;

  public LikeAndUnlikeService(LikeRepository likeRepository, LikableRepository likableRepository,
      AccountValidatorService accountValidatorService) {
    this.likeRepository = likeRepository;
    this.likableRepository = likableRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public LikeResponseDTO handle(String accountId, String targetId, LikeTargetType type) {
    boolean validatedAccount = this.accountValidatorService.verifyPresenceAndActive("_id", accountId);
    if (!validatedAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Likable likable = this.likableRepository.findById(targetId, type)
        .orElseThrow(() -> new ResourceNotFoundException("This resource cannot be found."));
    boolean isLiked = this.likeRepository.existsByTargetIdAndAccountId(targetId, accountId);
    if (!isLiked) {
      Like like = Like.create(accountId, targetId, type);
      this.likeRepository.save(like);
      likable.incrementLikeCount();
    } else {
      this.likeRepository.deleteByTargetIdAndAccountId(targetId, accountId);
      likable.decrementLikeCount();
    }
    this.likableRepository.save(likable);
    return new LikeResponseDTO(likable.getId(), likable.getLikeCount());
  };

}
