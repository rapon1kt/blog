package com.raponi.blog.application.service.report;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.raponi.blog.application.usecase.report.DeleteReportsByTargetIdUseCase;
import com.raponi.blog.application.validators.AccountValidatorService;
import com.raponi.blog.domain.model.ReportStatus;
import com.raponi.blog.domain.model.ReportTargetType;
import com.raponi.blog.domain.model.Reportable;
import com.raponi.blog.domain.repository.ReportRepository;
import com.raponi.blog.domain.repository.ReportableRepository;
import com.raponi.blog.presentation.errors.AccessDeniedException;
import com.raponi.blog.presentation.errors.BusinessRuleException;
import com.raponi.blog.presentation.errors.ResourceNotFoundException;

@Service
public class DeleteReportsByTargetIdService implements DeleteReportsByTargetIdUseCase {

  private final ReportRepository reportRepository;
  private final ReportableRepository reportableRepository;
  private final AccountValidatorService accountValidatorService;

  public DeleteReportsByTargetIdService(ReportRepository reportRepository,
      ReportableRepository reportableRepository,
      AccountValidatorService accountValidatorService) {
    this.reportRepository = reportRepository;
    this.reportableRepository = reportableRepository;
    this.accountValidatorService = accountValidatorService;
  }

  public String handle(String targetId, ReportTargetType type) {
    boolean isAdmin = this.accountValidatorService.isAdmin();
    if (!isAdmin)
      throw new AccessDeniedException("You don't have permission to do this.");
    Optional<Reportable> reportTarget = this.reportableRepository.findById(targetId, type);
    if (reportTarget.isPresent()) {
      boolean existsOpen = this.reportRepository.existsByTargetIdAndStatus(targetId, ReportStatus.OPEN);
      boolean existsInReview = this.reportRepository.existsByTargetIdAndStatus(targetId, ReportStatus.IN_REVIEW);

      if (!existsOpen && !existsInReview) {
        this.reportRepository.deleteByTargetId(targetId);
        return "All reports about " + type + " - " + targetId + " are deleted with success.";
      }
      throw new BusinessRuleException(
          "The reports about this content cannot be deleted, some of them are open or in review.");
    }
    throw new ResourceNotFoundException("The target of report was deleted.");
  }

}
