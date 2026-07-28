package com.raponi.blog.application.service.account;

import com.raponi.blog.application.usecase.account.BlockUnblockAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.AccountNotFoundException;
import com.raponi.blog.domain.exception.BusinessRuleException;
import com.raponi.blog.domain.model.Block;
import com.raponi.blog.domain.model.BlockResult;
import com.raponi.blog.domain.repository.*;
import org.springframework.stereotype.Service;

@Service
public class BlockAndUnblockAccountService implements BlockUnblockAccountUseCase {

  private final BlockRepository blockRepository;
  private final LikeRepository likeRepository;
  private final FollowRepository followRepository;
  private final CommentRepository commentRepository;
  private final AccountValidatorService accountValidatorService;

  public BlockAndUnblockAccountService(
    BlockRepository blockRepository,
    LikeRepository likeRepository,
    FollowRepository followRepository,
    CommentRepository commentRepository,
    AccountValidatorService accountValidatorService
  ) {
    this.blockRepository = blockRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.commentRepository = commentRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public BlockResult handle(String accountId, String blockedId) {
    if (accountId.equals(blockedId)) {
      throw new BusinessRuleException("You cannot block yourself.");
    }

    validateAccounts(accountId, blockedId);

    if (this.blockRepository.existsByBlockerIdAndBlockedId(accountId, blockedId)) {
      this.blockRepository.deleteByBlockerIdAndBlockedId(accountId, blockedId);
      return BlockResult.UNBLOCKED;
    }

    removeInteractions(accountId, blockedId);
    this.blockRepository.save(new Block(accountId, blockedId));

    return BlockResult.BLOCKED;
  }

  private void validateAccounts(String accountId, String blockedId) {
    if (!this.accountValidatorService.verifyAccountWithAccountId(accountId)) {
      throw new AccessDeniedException("You don't have permission to do this.");
    }
    if (!this.accountValidatorService.verifyPresenceAndActive("_id", blockedId)) {
      throw new AccountNotFoundException("The account you are trying to block is not valid.");
    }
  }

  private void removeInteractions(String blockerId, String blockedId) {
    this.followRepository.deleteByFollowerIdAndFollowingId(blockerId, blockedId);
    this.followRepository.deleteByFollowerIdAndFollowingId(blockedId, blockerId);
    this.likeRepository.deleteByTargetIdAndAccountId(blockedId, blockerId);
    this.commentRepository.deleteByAuthorIdAndTargetAuthorId(blockedId, blockerId);
  }
}
