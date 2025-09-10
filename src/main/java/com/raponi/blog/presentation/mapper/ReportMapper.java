package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Report;
import com.raponi.blog.infrastructure.persistence.entity.ReportEntity;
import com.raponi.blog.presentation.dto.ReportResponseDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {

  ReportEntity toEntity(Report report);

  Report toDomain(ReportEntity reportEntity);

  ReportResponseDTO toResponseDTO(Report report);

}
