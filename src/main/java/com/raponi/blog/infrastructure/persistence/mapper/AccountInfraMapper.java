package com.raponi.blog.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.raponi.blog.domain.model.Account;
import com.raponi.blog.infrastructure.persistence.document.AccountDocument;

@Mapper(componentModel = "spring")
public interface AccountInfraMapper {

  Account toDomain(AccountDocument document);

  AccountDocument toDocument(Account account);

}
