package com.raponi.blog.application.usecase.report;

import java.util.List;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;

public interface FindReportsByStatusUseCase {

  public List<Report> handle(ReportStatus status);

}
