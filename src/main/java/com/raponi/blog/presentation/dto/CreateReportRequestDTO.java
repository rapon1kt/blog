package com.raponi.blog.presentation.dto;

import com.raponi.blog.domain.model.ReportTargetType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateReportRequestDTO {

  @NotBlank(message = "Reason of report is required.")
  @Size(min = 8, message = "Please, describe in details")
  private String reason;

  @NotNull(message = "Report target type is required")
  private ReportTargetType reportType;

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public ReportTargetType getReportType() {
    return reportType;
  }

  public void setReportType(ReportTargetType reportType) {
    this.reportType = reportType;
  }

}
