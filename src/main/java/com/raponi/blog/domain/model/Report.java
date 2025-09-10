package com.raponi.blog.domain.model;

import java.time.Instant;

public class Report {
  private String id;
  private ReportStatus status;
  private String reporterId;
  private String targetId;
  private ReportTargetType reportTargetType;
  private String reason;
  private Instant createdAt;
  private Instant modifiedAt;

  private Report(String id, ReportStatus status, String reporterId, String targetId, ReportTargetType reportTargetType,
      String reason, Instant createdAt, Instant modifiedAt) {
    this.id = id;
    this.status = status;
    this.reporterId = reporterId;
    this.targetId = targetId;
    this.reportTargetType = reportTargetType;
    this.reason = reason;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public static Report create(String reporterId, String targetId, String reason, ReportTargetType reportTargetType) {
    Instant instant = Instant.now();
    return new Report(null, ReportStatus.OPEN, reporterId, targetId, reportTargetType, reason, instant, instant);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ReportStatus getStatus() {
    return status;
  }

  public void setStatus(ReportStatus status) {
    this.status = status;
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

  public ReportTargetType getReportType() {
    return reportTargetType;
  }

  public void setReportType(ReportTargetType reportTargetType) {
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
