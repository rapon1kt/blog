package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.application.usecase.ban.BanAccountCommand;
import com.raponi.blog.presentation.dto.request.BanAccountRequestDTO;

@Mapper(componentModel = "spring")
public interface BanMapper {
  
  BanAccountCommand toCommand(BanAccountRequestDTO requestDTO);

}
