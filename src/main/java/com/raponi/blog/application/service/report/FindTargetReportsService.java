package com.raponi.blog.application.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.FindTargetReportsUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.infrastructure.persistence.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;

@Service
public class FindTargetReportsService implements FindTargetReportsUseCase {

  private final AccountValidatorService accountValidatorService;
  private final ReportRepository reportRepository;

  public FindTargetReportsService(AccountValidatorService accountValidatorService, ReportRepository reportRepository) {
    this.accountValidatorService = accountValidatorService;
    this.reportRepository = reportRepository;
  }

  @Override
  public List<Report> handle(String accountId, String targetId) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (isAdmin) {
      return this.reportRepository.findByTargetId(targetId);
    }
    throw new AccessDeniedException("You don't have permission to do this.");
  }

}
