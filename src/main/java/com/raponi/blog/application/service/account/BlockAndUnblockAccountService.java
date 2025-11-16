package com.raponi.blog.application.service.account;

import java.util.List;

import org.springframework.stereotype.Service;
import com.raponi.blog.application.usecase.account.BlockUnblockAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Block;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.*;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class BlockAndUnblockAccountService implements BlockUnblockAccountUseCase {

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
      AccountValidatorService accountValidatorService) {
    this.blockRepository = blockRepository;
    this.postRepository = postRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.commentRepository = commentRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public String handle(String accountId, String blockedId) {
    if (accountId.equals(blockedId))
      throw new BusinessRuleException("You cannot block yourself.");

    boolean isAccountValid = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    boolean isBlockedAccountValid = this.accountValidatorService.verifyPresenceAndActive("_id", blockedId);

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
      this.followRepository.deleteByFollowerIdAndFollowingId(blockerId, blockedId);
    if (this.followRepository.existsByFollowerIdAndFollowingId(blockedId, blockerId))
      this.followRepository.deleteByFollowerIdAndFollowingId(blockedId, blockerId);
    List<Post> blockerPosts = this.postRepository.findByAuthorId(blockerId);
    blockerPosts.forEach(post -> {
      if (this.likeRepository.existsByTargetIdAndAccountId(post.getId(), blockedId))
        this.likeRepository.deleteByTargetIdAndAccountId(post.getId(), blockedId);
      List<Comment> comments = this.commentRepository.findByPostId(post.getId());
      comments.forEach(comment -> {
        if (comment.getAuthorId().equals(blockedId))
          this.commentRepository.deleteByAuthorId(blockedId);
        if (this.likeRepository.existsByTargetIdAndAccountId(comment.getId(), blockedId))
          this.likeRepository.deleteByTargetIdAndAccountId(comment.getId(), blockedId);
      });
      this.commentRepository.deleteByAuthorIdAndPostId(blockedId, post.getId());
    });
  }

}
