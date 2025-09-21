package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.service.follow.FollowAndUnfollowAccountService;
import com.raponi.blog.application.service.like.LikeAndUnlikeService;
import com.raponi.blog.application.usecase.account.BlockUnblockAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Block;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.*;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class BlockAndUnblockAccountService implements BlockUnblockAccountUseCase {

  private final LikeAndUnlikeService likeAndUnlikeService;
  private final FollowAndUnfollowAccountService followAndUnfollowAccountService;
  private final BlockRepository blockRepository;
  private final PostRepository postRepository;
  private final LikeRepository likeRepository;
  private final FollowRepository followRepository;
  private final CommentRepository commentRepository;
  private final AccountValidatorService accountValidatorService;

  public BlockAndUnblockAccountService(BlockRepository blockRepository,
      PostRepository postRepository,
      LikeRepository likeRepository,
      FollowRepository followRepository,
      CommentRepository commentRepository,
      AccountValidatorService accountValidatorService,
      FollowAndUnfollowAccountService followAndUnfollowAccountService, LikeAndUnlikeService likeAndUnlikeService) {
    this.blockRepository = blockRepository;
    this.postRepository = postRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.commentRepository = commentRepository;
    this.accountValidatorService = accountValidatorService;
    this.followAndUnfollowAccountService = followAndUnfollowAccountService;
    this.likeAndUnlikeService = likeAndUnlikeService;
  }

  @Override
  public String handle(String accountId, String blockedId) {
    if (accountId.equals(blockedId))
      throw new BusinessRuleException("You cannot block yourself.");

    boolean isAccountValid = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    boolean isBlockedAccountValid = this.accountValidatorService.verifyPresenceAndActive("_id", accountId);

    if (!isAccountValid)
      throw new AccessDeniedException("You don't have permission to do this.");

    if (!isBlockedAccountValid)
      throw new ResourceNotFoundException("The account you are trying to block is not valid.");

    boolean alreadyBlocked = this.blockRepository.existsByBlockerIdAndBlockedId(accountId, blockedId);

    if (alreadyBlocked) {
      this.blockRepository.deleteByBlockerIdAndBlockedId(accountId, blockedId);
      return "Account unblock with success!";
    }
    removeInteractions(accountId, blockedId);
    Block block = new Block(accountId, blockedId);
    this.blockRepository.save(block);
    return "Account blocked with success!";
  }

  private void removeInteractions(String blockerId, String blockedId) {
    if (this.followRepository.existsByFollowerIdAndFollowingId(blockerId, blockedId))
      this.followAndUnfollowAccountService.handle(blockerId, blockedId);
    List<Post> blockerPosts = this.postRepository.findByAuthorId(blockerId);
    blockerPosts.forEach(post -> {
      if (this.likeRepository.existsByTargetIdAndAccountId(post.getId(), blockedId)) {
        this.likeAndUnlikeService.handle(blockedId, post.getId(), LikeTargetType.POST);
      }
      List<Comment> comments = this.commentRepository.findByPostId(post.getId());
      comments.forEach(comment -> {
        if (comment.getAuthorId().equals(blockedId))
          this.commentRepository.deleteByAuthorId(blockedId);
        if (this.likeRepository.existsByTargetIdAndAccountId(comment.getId(), blockedId)) {
          this.likeAndUnlikeService.handle(blockedId, post.getId(), LikeTargetType.COMMENT);
        }
      });
      this.commentRepository.deleteByAuthorIdAndPostId(blockedId, post.getId());
    });
  }

}
