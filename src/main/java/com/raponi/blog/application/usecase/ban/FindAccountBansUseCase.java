package com.raponi.blog.application.usecase.ban;

import java.util.List;

import com.raponi.blog.domain.model.Ban;

public interface FindAccountBansUseCase {

  List<Ban> handle(String bannedId);

}
