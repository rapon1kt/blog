package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.like.LikeAndUnlikeService;
import com.raponi.blog.domain.model.LikeTargetType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/likes")
public class LikeController {

  private final LikeAndUnlikeService likeAndUnlikeService;

  public LikeController(LikeAndUnlikeService likeAndUnlikeService) {
    this.likeAndUnlikeService = likeAndUnlikeService;
  }

  @PostMapping("/{targetId}")
  public ResponseEntity<?> likeAndUnlike(@PathVariable("targetId") String targetId, @RequestParam LikeTargetType type,
      Authentication auth) {
    return ResponseEntity.status(201).body(this.likeAndUnlikeService.handle(auth.getName(), targetId, type));
  }

}
