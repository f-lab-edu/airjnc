package com.airjnc.ncp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.ncp.dto.NcpMailerReq;
import com.airjnc.ncp.dto.NcpMailerResp;
import com.airjnc.ncp.dto.NcpMailerSendDto;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.util.NcpApiUrl;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@UnitTest
class NcpMailerServiceTest {

  @Mock
  RestTemplate restTemplate;

  @Mock
  NcpBaseService ncpBaseService;

  @Mock
  NcpMailerProperties ncpMailerProperties;

  @Mock
  CommonCheckService commonCheckService;

  @InjectMocks
  NcpMailerService ncpMailerService;

  @Test
  void send() {
    //given
    String email = TestUser.EMAIL;
    String name = TestUser.NAME;
    String code = "123456";

    HttpEntity<String> entity = new HttpEntity<>("body");
    given(ncpBaseService.createEntity(eq(NcpApiUrl.Mailer.CREATE_MAIL_REQULEST), any(NcpMailerReq.class))).willReturn(
        entity);

    NcpMailerResp res = NcpMailerResp.builder().requestId("requestId").count(1).build();
    given(restTemplate.postForObject(NcpApiUrl.Mailer.CREATE_MAIL_REQULEST, entity, NcpMailerResp.class)).willReturn(
        res);
    //when
    ncpMailerService.send(NcpMailerSendDto.builder().email(email).name(name).code(code).build());
    //then
    then(ncpBaseService).should(times(1))
        .createEntity(eq(NcpApiUrl.Mailer.CREATE_MAIL_REQULEST), any(NcpMailerReq.class));
    then(restTemplate).should(times(1))
        .postForObject(NcpApiUrl.Mailer.CREATE_MAIL_REQULEST, entity, NcpMailerResp.class);
    then(commonCheckService).should(times(1)).shouldBeMatch(res.getCount(), 1);
  }
}
