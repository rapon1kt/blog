package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.posts.*;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.presentation.helpers.HttpHelper;

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
  public ResponseEntity<?> createPost(@RequestBody Post postBody, Authentication auth) {
    try {
      return HttpHelper.ok(this.createPostService.handle(postBody, auth.getName()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping
  public ResponseEntity<?> getAllPosts() {
    try {
      return HttpHelper.ok(this.findAllPostsService.handle());
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping("/{postId}")
  public ResponseEntity<?> findPostById(@PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.findPostByIdService.handle(postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @PutMapping("/{accountId}/{postId}")
  public ResponseEntity<?> updatePostStatus(@PathVariable("accountId") String accountId,
      @PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.updatePostStatusService.handle(accountId, postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/{accountId}/{postId}")
  public ResponseEntity<?> deletePostById(@PathVariable("accountId") String accountId,
      @PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.deletePostService.handle(accountId, postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}
