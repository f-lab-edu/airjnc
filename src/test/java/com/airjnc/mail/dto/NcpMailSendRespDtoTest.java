package com.airjnc.mail.dto;

import static org.assertj.core.api.Assertions.assertThat;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;

// ncp 에서 api 요청시에 보낼 수 있는 데이터의 스펙이 정해져 있고, 이에 맞춰서 잘 보내고 있는 지 확인하기 위해 테스트 진행A
@UnitTest
class NcpMailSendRespDtoTest {

  @Test
  void checkNcpMailerResponseFields() throws NoSuchFieldException {
    //when
    Class<NcpMailSendRespDto> clazz = NcpMailSendRespDto.class;
    String requestId = clazz.getDeclaredField("requestId").getType().getName();
    String count = clazz.getDeclaredField("count").getType().getName();
    //then
    assertThat(requestId.contains("String")).isTrue();
    assertThat(count).isEqualTo("int");
  }
}
