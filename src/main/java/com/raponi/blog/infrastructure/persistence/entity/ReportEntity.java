package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;

import lombok.Data;

@Data
@Document(collection = "reports")
public class ReportEntity {
  @Id
  private String id;
  private ReportStatus status;
  private String reporterId;
  private String targetId;
  private ReportTargetType reportType;
  private String reason;
  private Instant createdAt;
  private Instant modifiedAt;

  public ReportEntity(Report report) {
    this.id = report.getId();
    this.status = report.getStatus();
    this.reporterId = report.getReporterId();
    this.targetId = report.getTargetId();
    this.reportType = report.getReportType();
    this.reason = report.getReason();
    this.createdAt = report.getCreatedAt();
    this.modifiedAt = report.getModifiedAt();
  }

}
