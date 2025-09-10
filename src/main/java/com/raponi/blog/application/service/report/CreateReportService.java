package com.raponi.blog.application.service.report;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.CreateReportUseCase;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.repository.ReportableRepository;
import com.raponi.blog.infrastructure.persistence.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class CreateReportService implements CreateReportUseCase {

  private final ReportRepository reportRepository;
  private final ReportableRepository reportableRepository;

  public CreateReportService(ReportRepository reportRepository, ReportableRepository reportableRepository) {
    this.reportRepository = reportRepository;
    this.reportableRepository = reportableRepository;
  }

  @Override
  public Report handle(String accountId, String targetId, String reason, ReportTargetType reportTargetType) {
    var target = this.reportableRepository.findById(targetId, reportTargetType)
        .orElseThrow(() -> new ResourceNotFoundException("Content reported not found."));
    if (target.getAuthorId().equals(accountId))
      throw new AccessDeniedException("You cannot report your own content.");
    if (this.reportRepository.existsByReporterIdAndTargetId(accountId, targetId))
      throw new BusinessRuleException("You cannot report one content more than once.");

    Report report = Report.create(accountId, targetId, reason, reportTargetType);
    return this.reportRepository.save(report);
  }

}
