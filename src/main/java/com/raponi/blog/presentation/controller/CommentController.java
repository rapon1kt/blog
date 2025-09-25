package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.comment.*;
import com.raponi.blog.presentation.dto.CreateCommentRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(path = "/posts/{postId}/comments")
public class CommentController {

  private final CreateCommentService createCommentService;
  private final AnswerCommentService answerCommentService;
  private final DeleteCommentService deleteCommentService;

  public CommentController(CreateCommentService createCommentService, AnswerCommentService answerCommentService,
      DeleteCommentService deleteCommentService) {
    this.createCommentService = createCommentService;
    this.answerCommentService = answerCommentService;
    this.deleteCommentService = deleteCommentService;
  }

  @PostMapping
  public ResponseEntity<?> commentPost(@PathVariable("postId") String postId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO,
      Authentication auth) {
    return ResponseEntity.status(201).body(this.createCommentService.handle(auth.getName(), postId, requestDTO));
  }

  @PostMapping("/{commentId}")
  public ResponseEntity<?> answerComment(@PathVariable("commentId") String commentId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO, Authentication auth) {
    return ResponseEntity.status(201).body(this.answerCommentService.handle(auth.getName(), commentId, requestDTO));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<?> deletePost(@PathVariable("commentId") String commentId,
      @PathVariable("postId") String postId, Authentication auth) {
    return ResponseEntity.ok(this.deleteCommentService.handle(auth.getName(), commentId, postId));
  }

}
