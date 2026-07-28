package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.posts.*;
import com.raponi.blog.domain.model.PostVisibility;
import com.raponi.blog.presentation.dto.request.CreatePostRequestDTO;
import com.raponi.blog.presentation.mapper.PostMapper;

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

  private final PostMapper mapper;
  private final PinPostService pinPostService;
  private final CreatePostService createPostService;
  private final DeletePostService deletePostService;
  private final UpdatePostStatusService updatePostStatusService;

  public PostController(PostMapper mapper, CreatePostService createPostService,
      DeletePostService deletePostService,
      UpdatePostStatusService updatePostStatusService,
      PinPostService pinPostService) {
    this.mapper = mapper;
    this.pinPostService = pinPostService;
    this.createPostService = createPostService;
    this.deletePostService = deletePostService;
    this.updatePostStatusService = updatePostStatusService;
  }

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequestDTO requestDTO, Authentication auth) {
    var command = mapper.toCommand(requestDTO);
    var response = this.createPostService.handle(command, auth.getName());
    return ResponseEntity.status(201).body(response);
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
