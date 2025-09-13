package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.ReportTargetType;

public interface DeleteReportsByTargetIdUseCase {
  public String handle(String targetId, ReportTargetType type);
}
