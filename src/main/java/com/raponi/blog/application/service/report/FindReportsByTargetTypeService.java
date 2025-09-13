package com.raponi.blog.application.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindReportsByTargetTypeUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

@Service
public class FindReportsByTargetTypeService implements FindReportsByTargetTypeUseCase {

  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public FindReportsByTargetTypeService(ReportRepository reportRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public List<Report> handle(ReportTargetType type) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");
    return this.reportRepository.findByReportTargetType(type);

  }

}
