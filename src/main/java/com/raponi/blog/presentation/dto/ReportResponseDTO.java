package com.raponi.blog.presentation.dto;

import java.time.Instant;

import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;

public class ReportResponseDTO {
  private String id;
  private ReportStatus status;
  private ReportTargetType reportTargetType;
  private String reporterId;
  private String targetId;
  private String reason;
  private Instant createdAt;
  private Instant modifiedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReporterId() {
    return reporterId;
  }

  public void setReporterId(String reporterId) {
    this.reporterId = reporterId;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public ReportStatus getStatus() {
    return status;
  }

  public void setStatus(ReportStatus status) {
    this.status = status;
  }

  public ReportTargetType getReportTargetType() {
    return reportTargetType;
  }

  public void setReportTargetType(ReportTargetType reportTargetType) {
    this.reportTargetType = reportTargetType;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Instant modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

}
