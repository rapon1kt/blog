package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.account.*;
import com.raponi.blog.application.service.comment.FindAllCommentAnswersService;
import com.raponi.blog.application.service.comment.FindPostCommentsService;
import com.raponi.blog.application.service.like.FindTargetLikesService;
import com.raponi.blog.application.service.posts.FindAllPostsService;
import com.raponi.blog.application.service.posts.FindPostByIdService;
import com.raponi.blog.domain.model.LikeTargetType;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

  private final FindPostCommentsService findPostCommentsService;
  private final FindAllCommentAnswersService findAllCommentAnswersService;
  private final FindTargetLikesService findTargetLikesService;
  private final FindPostByIdService findPostByIdService;
  private final FindAllPostsService findAllPostsService;
  private final FindAccountPicturesService findAccountPicturesService;
  private final FindAccountFollowingService findAccountFollowingService;
  private final FindAccountFollowersService findAccountFollowersService;
  private final FindAccountPostsService findAccountPostsService;
  private final FindAccountByUsernameService findAccountByUsernameService;

  PublicController(FindAccountByUsernameService findAccountByUsernameService,
      FindAccountPostsService findAccountPostsService, FindAccountFollowersService findAccountFollowersService,
      FindAccountFollowingService findAccountFollowingService, FindAccountPicturesService findAccountPicturesService,
      FindAllPostsService findAllPostsService, FindPostByIdService findPostByIdService,
      FindTargetLikesService findTargetLikesService, FindAllCommentAnswersService findAllCommentAnswersService,
      FindPostCommentsService findPostCommentsService) {
    this.findAccountByUsernameService = findAccountByUsernameService;
    this.findAccountPostsService = findAccountPostsService;
    this.findAccountFollowersService = findAccountFollowersService;
    this.findAccountFollowingService = findAccountFollowingService;
    this.findAccountPicturesService = findAccountPicturesService;
    this.findAllPostsService = findAllPostsService;
    this.findPostByIdService = findPostByIdService;
    this.findTargetLikesService = findTargetLikesService;
    this.findAllCommentAnswersService = findAllCommentAnswersService;
    this.findPostCommentsService = findPostCommentsService;
  }

  /* ACCOUNT PUBLIC SERVICES */

  @GetMapping("/account/{username}")
  public ResponseEntity<?> getAccountByUsername(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountByUsernameService.handle(username));
  }

  @GetMapping("/account/{username}/posts")
  public ResponseEntity<?> getAccountPosts(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountPostsService.handle(username));
  }

  @GetMapping("/account/{username}/followers")
  public ResponseEntity<?> getAccountFollowers(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountFollowersService.handle(username));
  }

  @GetMapping("/account/{username}/following")
  public ResponseEntity<?> getAccountFollowing(@PathVariable("username") String username) {
    return ResponseEntity.ok(this.findAccountFollowingService.handle(username));
  }

  @GetMapping("/account/{username}/picture")
  public ResponseEntity<?> getAccountPicture(@PathVariable("username") String username) throws IOException {
    return ResponseEntity.ok(this.findAccountPicturesService.handle(username));
  }

  /* POST PUBLIC SERVICES */

  @GetMapping("/posts")
  public ResponseEntity<?> getAllPosts() {
    return ResponseEntity.ok(this.findAllPostsService.handle());
  }

  @GetMapping("/post/{postId}")
  public ResponseEntity<?> findPostById(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findPostByIdService.handle(postId));
  }

  @GetMapping("/post/{postId}/comments")
  public ResponseEntity<?> getPostComments(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.findPostCommentsService.handle(postId));
  }

  /* COMMENT PUBLIC SERVICES */

  @GetMapping("/comment/{commentId}")
  public ResponseEntity<?> getCommentAnswers(@PathVariable("commentId") String commentId) {
    return ResponseEntity.ok(this.findAllCommentAnswersService.handle(commentId));
  }

  /* LIKE PUBLIC SERVICE */

  @GetMapping("/target/{targetId}")
  public ResponseEntity<?> findTargetLikes(@PathVariable("targetId") String targetId,
      @RequestParam LikeTargetType type) {
    return ResponseEntity.ok(this.findTargetLikesService.handle(targetId, type));
  }

}
