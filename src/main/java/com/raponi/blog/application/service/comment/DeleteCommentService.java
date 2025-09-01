package com.raponi.blog.application.service.comment;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.raponi.blog.application.service.CommentValidatorService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.usecase.comment.DeleteCommentUseCase;
import com.raponi.blog.infrastructure.persistence.repository.CommentRepository;

@Service
public class DeleteCommentService implements DeleteCommentUseCase {

  private final CommentRepository commentRepository;
  private final CommentValidatorService commentValidatorService;

  public DeleteCommentService(CommentRepository commentRepository, CommentValidatorService commentValidatorService) {
    this.commentRepository = commentRepository;
    this.commentValidatorService = commentValidatorService;
  }

  @Override
  public String handle(String commentId, String postId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String accountId = auth.getName();
    String role = auth.getAuthorities().iterator().next().getAuthority();

    boolean isValidComment = this.commentValidatorService.isValidComment(commentId);
    if (isValidComment) {
      Comment comment = this.commentRepository.findById(commentId).get();
      if (comment.accountId().equals(accountId) || role.equals("ROLE_ADMIN")) {
        this.commentRepository.deleteById(commentId);
        return "Comentário deletado com sucesso!";
      }
    }
    throw new IllegalArgumentException("Não é possível");
  }

}
