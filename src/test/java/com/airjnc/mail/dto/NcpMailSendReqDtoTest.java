package com.airjnc.mail.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.mail.dto.NcpMailSendReqDto.Recipient;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;

// ncp 에서 api 요청시에 보낼 수 있는 데이터의 스펙이 정해져 있고, 이에 맞춰서 잘 보내고 있는 지 확인하기 위해 테스트 진행
@UnitTest
class NcpMailSendReqDtoTest {

  @Test
  void checkNcpMailerRequestFields() throws NoSuchFieldException {
    //when
    Class<NcpMailSendReqDto> clazz = NcpMailSendReqDto.class;
    String templateSid = clazz.getDeclaredField("templateSid").getType().getName();
    String recipients = clazz.getDeclaredField("recipients").getType().getName();
    String individual = clazz.getDeclaredField("individual").getType().getName();
    String advertising = clazz.getDeclaredField("advertising").getType().getName();
    //then
    assertThat(templateSid).isEqualTo("int");
    assertThat(recipients.contains("List")).isTrue();
    assertThat(individual).isEqualTo("boolean");
    assertThat(advertising).isEqualTo("boolean");
  }

  @Test
  void checkRecipientFields() throws NoSuchFieldException {
    //when
    Class<Recipient> clazz = Recipient.class;
    String address = clazz.getDeclaredField("address").getType().getName();
    String type = clazz.getDeclaredField("type").getType().getName();
    String parameters = clazz.getDeclaredField("parameters").getType().getName();
    //then
    assertThat(address.contains("String")).isTrue();
    assertThat(type.contains("Recipient$Type")).isTrue();
    assertThat(parameters.contains("Map")).isTrue();
  }
}
