package com.raponi.blog.infrastructure.persistence.mapper;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.infrastructure.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountInfraMapper {
  AccountEntity toEntity(Account account);

  Account toDomain(AccountEntity accountEntity);
}
