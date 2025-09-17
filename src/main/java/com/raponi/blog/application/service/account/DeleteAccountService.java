package com.raponi.blog.application.service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.account.DeleteAccountUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.domain.repository.*;
import com.raponi.blog.presentation.dto.DeleteAccountRequestDTO;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.InvalidParamException;

@Service
public class DeleteAccountService implements DeleteAccountUseCase {

  private final PasswordEncoder passwordEncoder;

  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  private final LikeRepository likeRepository;
  private final FollowRepository followRepository;
  private final CommentRepository commentRepository;
  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteAccountService(AccountRepository accountRepository, PostRepository postRepository,
      LikeRepository likeRepository, FollowRepository followRepository, CommentRepository commentRepository,
      ReportRepository reportRepository,
      AccountValidatorService accountValidatorService, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.postRepository = postRepository;
    this.likeRepository = likeRepository;
    this.followRepository = followRepository;
    this.commentRepository = commentRepository;
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String handle(String accountId, DeleteAccountRequestDTO request) {
    boolean isValidAccount = this.accountValidatorService.verifyAccountWithAccountId(accountId);
    if (!isValidAccount)
      throw new AccessDeniedException("You don't have permission to do this.");
    Account account = this.accountRepository.findById(accountId).get();
    verifyPasswordMatch(request.getPassword(), account.getPassword());
    deleteInteractions(accountId);
    return "Account with id equals " + accountId + " deleted with success";
  }

  private void verifyPasswordMatch(String password, String systemPassword) {
    if (!passwordEncoder.matches(password, systemPassword))
      throw new InvalidParamException("Your password does not match the system password.");
    return;
  }

  private void deleteInteractions(String accountId) {
    this.accountRepository.deleteById(accountId);
    this.postRepository.deleteByAccountId(accountId);
    this.likeRepository.deleteByAccountId(accountId);
    this.followRepository.deleteByFollowerId(accountId);
    this.followRepository.deleteByFollowingId(accountId);
    this.commentRepository.deleteByAccountId(accountId);
    this.reportRepository.deleteByReporterId(accountId);
  }

}
