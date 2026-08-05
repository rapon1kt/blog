package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.application.command.SignUpAccountCommand;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.request.SignUpAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.SignUpAccountResponseDTO;

@Mapper(componentModel = "spring")
public interface AccountPresentationMapper {

  SignUpAccountCommand toCommand(SignUpAccountRequestDTO requestDTO);

  SignUpAccountResponseDTO toResponse(Account account);

}
