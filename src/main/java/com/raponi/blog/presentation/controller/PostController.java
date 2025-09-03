package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.posts.*;
import com.raponi.blog.presentation.dto.CreatePostRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final CreatePostService createPostService;
  private final FindAllPostsService findAllPostsService;
  private final FindPostByIdService findPostByIdService;
  private final UpdatePostStatusService updatePostStatusService;
  private final DeletePostService deletePostService;

  public PostController(CreatePostService createPostService, FindAllPostsService findAllPostsService,
      DeletePostService deletePostService, FindPostByIdService findPostByIdService,
      UpdatePostStatusService updatePostStatusService) {
    this.createPostService = createPostService;
    this.findAllPostsService = findAllPostsService;
    this.findPostByIdService = findPostByIdService;
    this.deletePostService = deletePostService;
    this.updatePostStatusService = updatePostStatusService;
  }

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequestDTO requestDTO, Authentication auth) {
    return ResponseEntity.ok(this.createPostService.handle(requestDTO, auth.getName()));
  }

  @GetMapping
  public ResponseEntity<?> getAllPosts() {
    return ResponseEntity.ok(this.findAllPostsService.handle());
  }

  @GetMapping("/{postId}")
  public ResponseEntity<?> findPostById(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findPostByIdService.handle(postId));
  }

  @PutMapping("/{accountId}/{postId}")
  public ResponseEntity<?> updatePostStatus(@PathVariable("accountId") String accountId,
      @PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.updatePostStatusService.handle(accountId, postId));
  }

  @DeleteMapping("/{accountId}/{postId}")
  public ResponseEntity<?> deletePostById(@PathVariable("accountId") String accountId,
      @PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.deletePostService.handle(accountId, postId));
  }

}
