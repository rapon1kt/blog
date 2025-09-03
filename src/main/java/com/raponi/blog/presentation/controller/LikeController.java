package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.like.CountPostLikeService;
import com.raponi.blog.application.service.like.LikeAndUnlikePostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

  private final LikeAndUnlikePostService likeAndUnlikePostService;
  private final CountPostLikeService countPostLikeService;

  public LikeController(LikeAndUnlikePostService likeAndUnlikePostService, CountPostLikeService countPostLikeService) {
    this.likeAndUnlikePostService = likeAndUnlikePostService;
    this.countPostLikeService = countPostLikeService;
  }

  @PostMapping
  public ResponseEntity<?> likePost(@PathVariable("postId") String postId) {
    return ResponseEntity.status(201).body(this.likeAndUnlikePostService.handle(postId));
  }

  @GetMapping
  public ResponseEntity<?> countPostLikes(@PathVariable("postId") String postId) {
    return ResponseEntity.ok(this.countPostLikeService.handle(postId));
  }

}
