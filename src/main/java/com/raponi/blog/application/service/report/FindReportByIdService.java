package com.raponi.blog.application.service.report;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindReportByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class FindReportByIdService implements FindReportByIdUseCase {

  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public FindReportByIdService(ReportRepository reportRepository, AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public Report handle(String id) {
    boolean isAuthorized = this.accountValidatorService.isAdmin();
    if (!isAuthorized)
      throw new AccessDeniedException("You dont have permission to do this.");
    return this.reportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("This report cannot be found."));
  }

}
