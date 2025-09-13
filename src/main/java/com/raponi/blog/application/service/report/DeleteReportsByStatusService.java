package com.raponi.blog.application.service.report;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.DeleteReportsByStatusUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;

@Service
public class DeleteReportsByStatusService implements DeleteReportsByStatusUseCase {

  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteReportsByStatusService(ReportRepository reportRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public String handle(ReportStatus status) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");
    switch (status) {
      case OPEN:
        throw new BusinessRuleException("You cannot delete a report that are open.");
      case IN_REVIEW:
        throw new BusinessRuleException("You cannot delete a report that are in review.");
      default:
        this.reportRepository.deleteByStatus(status);
        return "All reports with status " + status + " are deleted with success.";
    }
  }

}
