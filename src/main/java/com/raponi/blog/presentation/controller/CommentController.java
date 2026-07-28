package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.comment.*;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.presentation.dto.request.CreateCommentRequestDTO;
import com.raponi.blog.presentation.mapper.CommentMapper;

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

  private final CommentMapper mapper;
  private final CreateCommentService createCommentService;
  private final AnswerCommentService answerCommentService;
  private final DeleteCommentService deleteCommentService;

  public CommentController(CommentMapper mapper, CreateCommentService createCommentService,
      AnswerCommentService answerCommentService,
      DeleteCommentService deleteCommentService) {
    this.mapper = mapper;
    this.createCommentService = createCommentService;
    this.answerCommentService = answerCommentService;
    this.deleteCommentService = deleteCommentService;
  }

  @PostMapping
  public ResponseEntity<Comment> commentPost(@PathVariable("postId") String postId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO,
      Authentication auth) {
    var command = this.mapper.toCommand(requestDTO);
    var response = this.createCommentService.handle(auth.getName(), postId, command);
    return ResponseEntity.status(201).body(response);
  }

  @PostMapping("/{commentId}")
  public ResponseEntity<Comment> answerComment(@PathVariable("commentId") String commentId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO, Authentication auth) {
    var command = this.mapper.toCommand(requestDTO);
    var response = this.answerCommentService.handle(auth.getName(), commentId, command);
    return ResponseEntity.status(201).body(response);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deletePost(@PathVariable("commentId") String commentId, Authentication auth) {
    return ResponseEntity.ok(this.deleteCommentService.handle(auth.getName(), commentId));
  }

}
