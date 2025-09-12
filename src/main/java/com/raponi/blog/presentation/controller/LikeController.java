package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.like.LikeAndUnlikePostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

  private final LikeAndUnlikePostService likeAndUnlikePostService;

  public LikeController(LikeAndUnlikePostService likeAndUnlikePostService) {
    this.likeAndUnlikePostService = likeAndUnlikePostService;
  }

  @PostMapping
  public ResponseEntity<?> likePost(@PathVariable("postId") String postId) {
    return ResponseEntity.status(201).body(this.likeAndUnlikePostService.handle(postId));
  }

}
