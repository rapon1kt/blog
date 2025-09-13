package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.Report;

public interface FindReportByIdUseCase {

  public Report handle(String id);

}
