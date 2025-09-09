package com.raponi.blog.application.usecase.report;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.presentation.dto.CreateReportRequestDTO;

public interface CreateReportUseCase {

  Report handle(String accountId, String contentId, CreateReportRequestDTO requestDTO);

}
