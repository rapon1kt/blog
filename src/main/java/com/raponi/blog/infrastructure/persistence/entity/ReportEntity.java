package com.raponi.blog.infrastructure.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private ReportTargetType reportTargetType;
  private String reason;
  private Instant createdAt;
  private Instant modifiedAt;

}
