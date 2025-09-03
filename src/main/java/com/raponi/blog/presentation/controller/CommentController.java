package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.comment.CreateCommentService;
import com.raponi.blog.application.service.comment.DeleteCommentService;
import com.raponi.blog.application.service.comment.FindPostCommentsService;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/posts/{postId}/comments")
public class CommentController {

  private final CreateCommentService createCommentService;
  private final FindPostCommentsService findAllCommentsService;
  private final DeleteCommentService deleteCommentService;

  public CommentController(CreateCommentService createCommentService, FindPostCommentsService findAllCommentsService,
      DeleteCommentService deleteCommentService) {
    this.createCommentService = createCommentService;
    this.findAllCommentsService = findAllCommentsService;
    this.deleteCommentService = deleteCommentService;
  }

  @PostMapping
  public ResponseEntity<?> commentPost(@PathVariable("postId") String postId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO,
      Authentication auth) {
    return ResponseEntity.ok(this.createCommentService.handle(auth.getName(), postId, requestDTO));
  }

  @GetMapping
  public ResponseEntity<?> getPostComments(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findAllCommentsService.handle(postId));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<?> deletePost(@PathVariable("commentId") String commentId,
      @PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.deleteCommentService.handle(commentId, postId));
  }

}
