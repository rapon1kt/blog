package com.raponi.blog.domain.usecase.account;

import java.io.IOException;

public interface FindAccountPicturesUseCase {
  public byte[] handle(String accountId) throws IOException;
}
