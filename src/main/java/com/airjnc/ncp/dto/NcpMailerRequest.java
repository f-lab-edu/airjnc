package com.airjnc.ncp.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

// ncp mail request 스펙 링크 [요청 파라미터 참고]
// https://api.ncloud-docs.com/docs/ai-application-service-cloudoutboundmailer-createmailrequest
@Getter
public class NcpMailerRequest {

  private final int templateSid;

  private final List<Recipient> recipients;

  private final boolean individual;

  private final boolean advertising;

  @Builder
  public NcpMailerRequest(int templateSid, List<Recipient> recipients) {
    this.templateSid = templateSid;
    this.recipients = recipients;
    this.individual = true;
    this.advertising = false;
  }

  @Getter
  public static class Recipient {

    private final String address;

    private final Type type;

    private final Map<String, String> parameters;

    @Builder
    public Recipient(String address, Type type, Map<String, String> parameters) {
      this.address = address;
      this.type = type;
      this.parameters = parameters;
    }

    // https://api.ncloud-docs.com/docs/common-apidatatype-nesrecipientrequest
    public enum Type {
      // 수신자 유형 -> R: 수신자, C: 참조인, B: 숨은 참조
      R, C, B,
    }
  }
}
