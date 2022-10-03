package com.airjnc.reservation.domain;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationDateEntity {

  private Long id;

  private LocalDate date;

  private Boolean isCanceled;

  private Long roomId;

  private Long roomReservationId;

  @Builder
  public ReservationDateEntity(LocalDate date, Boolean isCanceled, Long roomId, Long roomReservationId) {
    this.date = date;
    this.isCanceled = isCanceled;
    this.roomId = roomId;
    this.roomReservationId = roomReservationId;
  }
}
