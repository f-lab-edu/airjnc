package com.testutil.fixture.user;

import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.testutil.testdata.TestUser;

public class UserInquiryEmailResDTOFixture {

  public static UserInquiryEmailResp.UserInquiryEmailRespBuilder getBuilder() {
    return UserInquiryEmailResp.builder()
        .id(TestUser.ID)
        .email(TestUser.EMAIL)
        .isActive(TestUser.IS_ACTIVE)
        .deletedAt(TestUser.CREATED_AT);
  }
}
