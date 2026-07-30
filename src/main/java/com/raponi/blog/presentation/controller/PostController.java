package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.comment.CreateCommentService;
import com.raponi.blog.application.service.comment.FindPostCommentsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.posts.*;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.presentation.dto.request.CreateCommentRequestDTO;
import com.raponi.blog.presentation.dto.request.CreatePostRequestDTO;
import com.raponi.blog.presentation.mapper.CommentMapper;
import com.raponi.blog.presentation.mapper.PostMapper;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostMapper postMapper;
  private final CommentMapper commentMapper;
  private final PinPostService pinPostService;
  private final CreatePostService createPostService;
  private final DeletePostService deletePostService;
  private final FindPostByIdService findPostByIdService;
  private final CreateCommentService createCommentService;
  private final FindPostCommentsService findPostCommentsService;
  private final UpdatePostStatusService updatePostStatusService;

  public PostController(
      PostMapper postMapper,
      CommentMapper commentMapper,
      PinPostService pinPostService,
      CreatePostService createPostService,
      DeletePostService deletePostService,
      FindPostByIdService findPostByIdService,
      CreateCommentService createCommentService,
      UpdatePostStatusService updatePostStatusService,
      FindPostCommentsService findPostCommentsService) {
    this.postMapper = postMapper;
    this.commentMapper = commentMapper;
    this.pinPostService = pinPostService;
    this.createPostService = createPostService;
    this.deletePostService = deletePostService;
    this.findPostByIdService = findPostByIdService;
    this.createCommentService = createCommentService;
    this.updatePostStatusService = updatePostStatusService;
    this.findPostCommentsService = findPostCommentsService;
  }

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequestDTO requestDTO, Authentication auth) {
    var command = this.postMapper.toCommand(requestDTO);
    var response = this.createPostService.handle(command, auth.getName());
    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<?> findPostById(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findPostByIdService.handle(postId));
  }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<Comment> commentPost(@PathVariable("postId") String postId,
      @RequestBody @Valid CreateCommentRequestDTO requestDTO,
      Authentication auth) {
    var command = this.commentMapper.toCommand(requestDTO);
    var response = this.createCommentService.handle(auth.getName(), postId, command);
    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/{postId}/comments")
  public ResponseEntity<?> getPostComments(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findPostCommentsService.handle(postId));
  }

  @PatchMapping("/{postId}/pin")
  public ResponseEntity<?> pinPost(@PathVariable("postId") String postId, Authentication auth) {
    return ResponseEntity.ok(this.pinPostService.handle(auth.getName(), postId));
  }

  @PatchMapping("/{postId}/visibility")
  public ResponseEntity<?> updatePostStatus(@PathVariable("postId") String postId, Authentication auth,
      @RequestParam("visibility") PostVisibility visibility) {
    return ResponseEntity.ok(this.updatePostStatusService.handle(auth.getName(), postId, visibility));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePostById(@PathVariable("postId") String postId, Authentication auth) {
    return ResponseEntity.ok(this.deletePostService.handle(auth.getName(), postId));
  }

}
