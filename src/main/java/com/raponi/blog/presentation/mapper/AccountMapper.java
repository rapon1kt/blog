package com.raponi.blog.presentation.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.AccountResponseDTO;

@Mapper
public interface AccountMapper {
  AccountResponseDTO toResponse(Account account);
}
