package com.raponi.blog.application.usecase.report;

import java.util.List;

import com.raponi.blog.domain.model.Report;

public interface FindTargetReportsUseCase {

  public List<Report> handle(String accountId, String targetId);

}
