package com.airjnc.ncp.dto;

import lombok.Builder;
import lombok.Getter;

// ncp mail response 스펙 링크 [요청 파라미터 참고]
// https://api.ncloud-docs.com/docs/common-apidatatype-nesrecipientrequest
@Getter
public class NcpMailerResponse {

  private final String requestId;

  private final int count;

  @Builder
  public NcpMailerResponse(String requestId, int count) {
    this.requestId = requestId;
    this.count = count;
  }
}
