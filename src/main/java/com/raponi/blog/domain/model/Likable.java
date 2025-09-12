package com.raponi.blog.domain.model;

public interface Likable {

  String getId();

  String getAuthorId();

  long getLikeCount();

  void incrementLikeCount();

  void decrementLikeCount();
}
