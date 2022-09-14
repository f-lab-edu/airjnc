package com.airjnc.reservation.dto.request;


import com.airjnc.common.annotation.TwoFieldDate;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TwoFieldDate(first = "startDate", second = "endDate")
public class ReservationReq {

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate startDate;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate endDate;

  @NotNull
  Integer guestCount;

  @Builder
  public ReservationReq(LocalDate startDate, LocalDate endDate, Integer guestCount) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.guestCount = guestCount;
  }

  public List<LocalDate> dateToList() {
    return List.of(startDate, endDate);
  }
}
