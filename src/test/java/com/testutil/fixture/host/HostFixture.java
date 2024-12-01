package com.testutil.fixture.host;

import com.airjnc.host.domain.HostEntity;

public class HostFixture {

    public static final Long ID = 1L;

    public static final String PHOTO_URL = "photo_url_test_string";

    public static final String DESCRIPTION = "안녕하세요 호스트설명입니다.";

    public static final Long USER_ID = 10L;


    public static HostEntity.HostEntityBuilder getHostEntityBuilder (){
        return HostEntity.builder()
                .id(ID)
                .photoURL(PHOTO_URL)
                .description(DESCRIPTION)
                .userId(USER_ID);
    }
}
