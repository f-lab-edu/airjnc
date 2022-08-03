package com.testutil.fixture;

import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.testutil.testdata.TestUser;

public class UserInquiryEmailResDTOFixture {

  public static UserInquiryEmailResp.UserInquiryEmailRespBuilder getBuilder() {
    return UserInquiryEmailResp.builder()
        .id(1L)
        .email(TestUser.EMAIL)
        .isActive(true)
        .deletedAt(TestUser.CREATED_AT);
  }
}
