package com.testutil.fixture;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.dto.PasswordMatchDTO;

public class PasswordMatchDTOFixture {

  public static final String PLAIN = "plain";

  public static final String HASH = BCryptHashEncrypter.encrypt(PasswordMatchDTOFixture.PLAIN);

  public static PasswordMatchDTO.PasswordMatchDTOBuilder getBuilder() {
    return PasswordMatchDTO.builder().password(PLAIN).hash(HASH);
  }
}
