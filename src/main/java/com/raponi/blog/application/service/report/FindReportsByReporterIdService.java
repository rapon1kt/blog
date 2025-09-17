package com.raponi.blog.application.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindReportsByReporterIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

@Service
public class FindReportsByReporterIdService implements FindReportsByReporterIdUseCase {

  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public FindReportsByReporterIdService(ReportRepository reportRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public List<Report> handle(String reporterId) {
    boolean isAuthorized = this.accountValidatorService.verifyAccountWithAccountId(reporterId);
    if (!isAuthorized)
      throw new AccessDeniedException("You cannot see the reports of this account.");
    return this.reportRepository.findByReporterId(reporterId);

  }

}
