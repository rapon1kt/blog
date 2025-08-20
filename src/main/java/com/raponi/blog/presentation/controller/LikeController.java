package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.like.CountPostLikeService;
import com.raponi.blog.application.service.like.LikeAndUnlikePostService;
import com.raponi.blog.presentation.helpers.HttpHelper;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
  public ResponseEntity<?> likePost(@PathVariable("postId") String postId, Authentication auth) {
    try {
      return HttpHelper.ok(this.likeAndUnlikePostService.handle(postId, auth.getName()));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping
  public ResponseEntity<?> countPostLikes(@PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.countPostLikeService.handle(postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}
