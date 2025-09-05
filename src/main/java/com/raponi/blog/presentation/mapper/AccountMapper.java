package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.AccountResponseDTO;
import com.raponi.blog.presentation.dto.CreatedAccountResponseDTO;
import com.raponi.blog.presentation.dto.PublicAccountResponseDTO;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  AccountResponseDTO toResponse(Account account);

  CreatedAccountResponseDTO toCreatedResponse(Account account);

  PublicAccountResponseDTO toPublicAccountResponseDTO(Account account);
}
