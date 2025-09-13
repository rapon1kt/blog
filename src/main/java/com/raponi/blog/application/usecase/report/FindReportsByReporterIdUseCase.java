package com.raponi.blog.application.usecase.report;

import java.util.List;

import com.raponi.blog.domain.model.Report;

public interface FindReportsByReporterIdUseCase {

  public List<Report> handle(String reporterId);

}
