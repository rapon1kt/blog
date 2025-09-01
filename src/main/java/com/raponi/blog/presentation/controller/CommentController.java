package com.raponi.blog.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raponi.blog.application.service.comment.CreateCommentService;
import com.raponi.blog.application.service.comment.DeleteCommentService;
import com.raponi.blog.application.service.comment.FindPostCommentsService;
import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.presentation.helpers.HttpHelper;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/posts/{postId}/comments")
public class CommentController {

  private final CreateCommentService createCommentService;
  private final FindPostCommentsService findAllCommentsService;
  private final DeleteCommentService deleteCommentService;

  public CommentController(CreateCommentService createCommentService, FindPostCommentsService findAllCommentsService,
      DeleteCommentService deleteCommentService) {
    this.createCommentService = createCommentService;
    this.findAllCommentsService = findAllCommentsService;
    this.deleteCommentService = deleteCommentService;
  }

  @PostMapping
  public ResponseEntity<?> commentPost(@PathVariable("postId") String postId, @RequestBody Comment comment,
      Authentication auth) {
    try {
      return HttpHelper.ok(this.createCommentService.handle(auth.getName(), postId, comment));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @GetMapping
  public ResponseEntity<?> getPostComments(@PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.findAllCommentsService.handle(postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<?> deletePost(@PathVariable("commentId") String commentId,
      @PathVariable("postId") String postId) {
    try {
      return HttpHelper.ok(this.deleteCommentService.handle(commentId, postId));
    } catch (Exception e) {
      return HttpHelper.badRequest(e);
    }
  }

}
