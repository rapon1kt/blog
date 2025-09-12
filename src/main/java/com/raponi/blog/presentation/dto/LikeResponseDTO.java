package com.raponi.blog.presentation.dto;

public class LikeResponseDTO {
  private String targetId;
  private long likeCount;

  public LikeResponseDTO(String targetId, long likeCount) {
    this.targetId = targetId;
    this.likeCount = likeCount;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }

}
