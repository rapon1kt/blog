package com.raponi.blog.presentation.mapper;

import com.raponi.blog.application.usecase.account.CreateAccountCommand;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.request.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.CreatedAccountResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  CreateAccountCommand toCommand(CreateAccountRequestDTO requestDTO);
  CreatedAccountResponseDTO toCreated(Account account);
}
