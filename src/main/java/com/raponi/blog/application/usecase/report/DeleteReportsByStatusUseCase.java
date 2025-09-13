package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.ReportStatus;

public interface DeleteReportsByStatusUseCase {
  public String handle(ReportStatus status);
}
