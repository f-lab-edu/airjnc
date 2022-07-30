package com.airjnc.ncp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.dto.NcpMailerReq;
import com.airjnc.ncp.dto.NcpMailerRes;
import com.airjnc.ncp.dto.NcpMailerSendDTO;
import com.airjnc.ncp.util.NcpMailerUrl;
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
  NcpCommonService ncpCommonService;

  @Mock
  NcpMailerProperties ncpMailerProperties;

  @Mock
  CommonInternalCheckService commonInternalCheckService;

  @InjectMocks
  NcpMailerService ncpMailerService;

  @Test
  void send() {
    //given
    String email = TestUser.EMAIL;
    String name = TestUser.NAME;
    String code = "123456";

    HttpEntity<String> entity = new HttpEntity<>("body");
    given(ncpCommonService.createEntity(eq(NcpMailerUrl.CREATE_MAIL_REQULEST), any(NcpMailerReq.class))).willReturn(
        entity);

    NcpMailerRes res = NcpMailerRes.builder().requestId("requestId").count(1).build();
    given(restTemplate.postForObject(NcpMailerUrl.CREATE_MAIL_REQULEST, entity, NcpMailerRes.class)).willReturn(
        res);
    //when
    ncpMailerService.send(NcpMailerSendDTO.builder().email(email).name(name).code(code).build());
    //then
    then(ncpCommonService).should(times(1))
        .createEntity(eq(NcpMailerUrl.CREATE_MAIL_REQULEST), any(NcpMailerReq.class));
    then(restTemplate).should(times(1))
        .postForObject(NcpMailerUrl.CREATE_MAIL_REQULEST, entity, NcpMailerRes.class);
    then(commonInternalCheckService).should(times(1)).shouldBeMatch(res.getCount(), 1);
  }
}
