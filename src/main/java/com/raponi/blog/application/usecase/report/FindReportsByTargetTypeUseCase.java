package com.raponi.blog.application.usecase.report;

import java.util.List;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportTargetType;

public interface FindReportsByTargetTypeUseCase {

  public List<Report> handle(ReportTargetType type);

}
