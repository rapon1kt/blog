package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportTargetType;

public interface CreateReportUseCase {

  Report handle(String accountId, String targetId, String reason, ReportTargetType reportTargetType);

}
