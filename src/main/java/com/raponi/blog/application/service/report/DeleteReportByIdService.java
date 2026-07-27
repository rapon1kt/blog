package com.raponi.blog.application.service.report;

import com.raponi.blog.application.usecase.report.DeleteReportByIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.exception.AccessDeniedException;
import com.raponi.blog.domain.exception.BusinessRuleException;
import com.raponi.blog.domain.model.Report;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteReportByIdService implements DeleteReportByIdUseCase {

  private final ReportRepository reportRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteReportByIdService(ReportRepository reportRepository, AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public String handle(String id) {
    Report report = this.reportRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("This report cannot be found."));
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");

    switch (report.getStatus()) {
      case OPEN:
        throw new BusinessRuleException("You cannot delete a report that are open.");
      case IN_REVIEW:
        throw new BusinessRuleException("You cannot delete a report that are in review.");
      default:
        this.reportRepository.deleteById(id);
        return "Report " + id + " deleted with success!";
    }
  }
}
