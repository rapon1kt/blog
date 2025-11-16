package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.posts.*;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.presentation.dto.CreatePostRequestDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PinPostService pinPostService;
  private final CreatePostService createPostService;
  private final UpdatePostStatusService updatePostStatusService;
  private final DeletePostService deletePostService;

  public PostController(CreatePostService createPostService,
      DeletePostService deletePostService,
      UpdatePostStatusService updatePostStatusService,
      PinPostService pinPostService) {
    this.createPostService = createPostService;
    this.deletePostService = deletePostService;
    this.updatePostStatusService = updatePostStatusService;
    this.pinPostService = pinPostService;
  }

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequestDTO requestDTO, Authentication auth) {
    return ResponseEntity.status(201).body(this.createPostService.handle(requestDTO, auth.getName()));
  }

  @PatchMapping("/{postId}")
  public ResponseEntity<?> updatePostStatus(@PathVariable("postId") String postId, Authentication auth,
      @RequestParam("visibility") PostVisibility visibility) {
    return ResponseEntity.ok(this.updatePostStatusService.handle(auth.getName(), postId, visibility));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePostById(@PathVariable("postId") String postId, Authentication auth) {
    return ResponseEntity.ok(this.deletePostService.handle(auth.getName(), postId));
  }

  @PatchMapping("/pin/{postId}")
  public ResponseEntity<?> pinPost(@PathVariable("postId") String postId, Authentication auth) {
    return ResponseEntity.ok(this.pinPostService.handle(auth.getName(), postId));
  }

}
