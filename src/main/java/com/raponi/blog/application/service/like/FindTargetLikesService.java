package com.raponi.blog.application.service.like;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.like.FindTargetLikesUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.application.validators.CommentValidatorService;
import com.raponi.blog.application.validators.PostValidatorService;
import com.raponi.blog.domain.model.Likable;
import com.raponi.blog.domain.model.Like;
import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.domain.repository.LikableRepository;
import com.raponi.blog.domain.repository.LikeRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;

@Service
public class FindTargetLikesService implements FindTargetLikesUseCase {

  private final LikeRepository likeRepository;
  private final LikableRepository likableRepository;
  private final PostValidatorService postValidatorService;
  private final CommentValidatorService commentValidatorService;
  private final AccountValidatorService accountValidatorService;

  public FindTargetLikesService(LikeRepository likeRepository, LikableRepository likableRepository,
      PostValidatorService postValidatorService,
      CommentValidatorService commentValidatorService, AccountValidatorService accountValidatorService) {
    this.likeRepository = likeRepository;
    this.likableRepository = likableRepository;
    this.postValidatorService = postValidatorService;
    this.commentValidatorService = commentValidatorService;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Like> handle(String targetId, LikeTargetType type) {

    switch (type) {
      case POST:
        boolean isValidPost = this.postValidatorService.validatePostPresenceAndPrivate(targetId);
        if (!isValidPost)
          throw new AccessDeniedException("You don't have permission to view the likes of this post.");
        break;
      case COMMENT:
        boolean isValidComment = this.commentValidatorService.isValidComment(targetId);
        if (!isValidComment)
          throw new AccessDeniedException("You don't have permission to view the likes of this comment.");
        break;
      default:
        throw new BusinessRuleException("Type of content invalid");
    }
    Likable likable = this.likableRepository.findById(targetId, type).get();
    boolean isViewerBlocked = this.accountValidatorService.isBlocked(likable.getAuthorId());
    if (isViewerBlocked) {
      return null;
    } else {
      List<Like> likes = this.likeRepository.findByTargetId(targetId);
      List<Like> likesOfNonBlocked = new ArrayList<Like>();
      likes.forEach(like -> {
        if (!this.accountValidatorService.isBlocked(like.getAccountId()))
          likesOfNonBlocked.add(like);
      });
      return likesOfNonBlocked;
    }
  }

}
