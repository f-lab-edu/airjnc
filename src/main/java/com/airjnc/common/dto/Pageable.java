package com.airjnc.common.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pageable {

  @Min(0)
  @NotNull
  private Integer skip;

  @Range(min = 0, max = 100)
  @NotNull
  private Integer take;

  @Builder
  public Pageable(Integer skip, Integer take) {
    this.skip = skip;
    this.take = take;
  }

  public Integer getTake() {
    return skip + take;
  }
}
