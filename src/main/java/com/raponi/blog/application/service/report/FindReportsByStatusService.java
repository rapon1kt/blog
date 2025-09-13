package com.raponi.blog.application.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindReportsByStatusUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

@Service
public class FindReportsByStatusService implements FindReportsByStatusUseCase {
  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public FindReportsByStatusService(ReportRepository reportRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public List<Report> handle(ReportStatus status) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");
    return this.reportRepository.findByStatus(status);
  }

}
