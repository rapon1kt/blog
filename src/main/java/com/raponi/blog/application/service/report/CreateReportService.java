package com.raponi.blog.application.service.report;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.CreateReportUseCase;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.infrastructure.persistence.repository.ReportRepository;
import com.raponi.blog.presentation.dto.CreateReportRequestDTO;
import com.raponi.blog.presentation.errors.BusinessRuleException;

@Service
public class CreateReportService implements CreateReportUseCase {

  private final ReportRepository reportRepository;

  public CreateReportService(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @Override
  public Report handle(String accountId, String targetId, CreateReportRequestDTO requestDTO) {
    boolean isFlood = this.reportRepository.existsByReporterIdAndTargetId(accountId, targetId);
    if (isFlood)
      throw new BusinessRuleException(
          "You cannot report: " + requestDTO.getReportType() + " - " + targetId + " more than once.");

    Report report = Report.create(accountId, targetId, requestDTO.getReason(), requestDTO.getReportType());
    return this.reportRepository.save(report);
  }

}
