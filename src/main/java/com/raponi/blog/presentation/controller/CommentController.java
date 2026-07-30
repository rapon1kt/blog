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
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {

  private final CommentMapper mapper;
  private final AnswerCommentService answerCommentService;
  private final DeleteCommentService deleteCommentService;
  private final FindAllCommentAnswersService findAllCommentAnswersService;

  public CommentController(
      CommentMapper mapper,
      AnswerCommentService answerCommentService,
      DeleteCommentService deleteCommentService,
      FindAllCommentAnswersService findAllCommentAnswersService) {
    this.mapper = mapper;
    this.answerCommentService = answerCommentService;
    this.deleteCommentService = deleteCommentService;
    this.findAllCommentAnswersService = findAllCommentAnswersService;
  }

  @PostMapping("/{commentId}/answers")
  public ResponseEntity<Comment> answerComment(@PathVariable("commentId") String commentId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO, Authentication auth) {
    var command = this.mapper.toCommand(requestDTO);
    var response = this.answerCommentService.handle(auth.getName(), commentId, command);
    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/{commentId}/answers")
  public ResponseEntity<?> getCommentAnswers(@PathVariable("commentId") String commentId) {
    return ResponseEntity.ok(this.findAllCommentAnswersService.handle(commentId));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deletePost(@PathVariable("commentId") String commentId, Authentication auth) {
    return ResponseEntity.ok(this.deleteCommentService.handle(auth.getName(), commentId));
  }

}
