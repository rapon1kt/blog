package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;

public interface UpdateReportStatusUseCase {
  public Report handle(String id, ReportStatus reportStatus);
}
