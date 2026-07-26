package com.raponi.blog.presentation.mapper;

import com.raponi.blog.application.usecase.account.CreateAccountCommand;
import com.raponi.blog.application.usecase.account.LoginAccountCommand;
import com.raponi.blog.domain.model.Account;
import com.raponi.blog.presentation.dto.request.CreateAccountRequestDTO;
import com.raponi.blog.presentation.dto.request.LoginAccountRequestDTO;
import com.raponi.blog.presentation.dto.response.CreatedAccountResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
  CreateAccountCommand toCreateCommand(CreateAccountRequestDTO requestDTO);
  CreatedAccountResponseDTO toCreated(Account account);

  LoginAccountCommand toLoginCommand(LoginAccountRequestDTO requestDTO);
}
