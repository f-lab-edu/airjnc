package com.airjnc.common.dto.response;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExceptionResp {

  private final String exceptionType;

  private final List<String> global;

  private final Map<String, String> field;

  public ExceptionResp(Exception ex, List<String> global, Map<String, String> field) {
    exceptionType = ex.getClass().getSimpleName();
    this.global = global;
    this.field = field;
  }
}
