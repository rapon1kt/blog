package com.raponi.blog.application.service.report;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.UpdateReportStatusUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.Reportable;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.domain.repository.ReportableRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class UpdateReportStatusService implements UpdateReportStatusUseCase {

  private final ReportRepository reportRepository;
  private final ReportableRepository reportableRepository;
  private final AccountValidatorService accountValidatorService;

  public UpdateReportStatusService(ReportRepository reportRepository, ReportableRepository reportableRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.reportableRepository = reportableRepository;
    this.accountValidatorService = accountValidatorService;
  }

  @Override
  public Report handle(String id, ReportStatus reportStatus) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");
    Report report = this.reportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("This report cannot be found."));
    Optional<Reportable> reportTarget = this.reportableRepository.findById(report.getTargetId(),
        report.getReportTargetType());
    if (!reportTarget.isPresent()) {
      report.setStatus(ReportStatus.RESOLVED);
      return this.reportRepository.save(report);
    }
    report.setStatus(reportStatus);
    return this.reportRepository.save(report);
  }

}
