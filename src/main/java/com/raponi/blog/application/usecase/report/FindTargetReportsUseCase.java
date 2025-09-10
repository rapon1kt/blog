package com.raponi.blog.application.usecase.report;

import java.util.List;

import com.raponi.blog.presentation.dto.ReportResponseDTO;

public interface FindTargetReportsUseCase {

  public List<ReportResponseDTO> handle(String accountId, String targetId);

}
