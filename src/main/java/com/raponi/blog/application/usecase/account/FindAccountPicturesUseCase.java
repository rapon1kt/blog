package com.raponi.blog.application.usecase.account;

import java.io.IOException;

public interface FindAccountPicturesUseCase {
  public byte[] handle(String accountId) throws IOException;
}
