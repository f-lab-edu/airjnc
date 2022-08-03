package com.testutil.fixture;

import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.testutil.testdata.TestUser;

public class UserInquiryEmailReqDTOFixture {

  public static UserInquiryEmailReq.UserInquiryEmailReqBuilder getBuilder() {
    return UserInquiryEmailReq.builder()
        .name(TestUser.NAME)
        .birthDate(TestUser.BIRTH_DATE);
  }
}
