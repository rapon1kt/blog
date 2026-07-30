package com.raponi.blog.presentation.controller;

import com.raponi.blog.application.service.like.FindTargetLikesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.like.LikeAndUnlikeService;
import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.domain.model.LikeType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/likes")
public class LikeController {

  private final LikeAndUnlikeService likeAndUnlikeService;
  private final FindTargetLikesService findTargetLikesService;

  public LikeController(LikeAndUnlikeService likeAndUnlikeService, FindTargetLikesService findTargetLikesService) {
    this.likeAndUnlikeService = likeAndUnlikeService;
    this.findTargetLikesService = findTargetLikesService;
  }

  @PostMapping("/{targetId}")
  public ResponseEntity<?> likeAndUnlike(@PathVariable("targetId") String targetId,
      @RequestParam("likeType") LikeType likeType, @RequestParam LikeTargetType targetType,
      Authentication auth) {
    return ResponseEntity.status(200)
        .body(this.likeAndUnlikeService.handle(auth.getName(), targetId, likeType, targetType));
  }

  @GetMapping("/{targetId}")
  public ResponseEntity<?> findTargetLikes(@PathVariable("targetId") String targetId,
      @RequestParam LikeTargetType type) {
    return ResponseEntity.ok(this.findTargetLikesService.handle(targetId, type));
  }

}
