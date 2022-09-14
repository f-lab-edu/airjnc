package com.airjnc.reservation.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationEntity {

  private Long id;

  private Integer userCount;

  private LocalDate startDate;

  private LocalDate endDate;

  private Integer totalPrice;

  private Long userId;

  private Long roomId;

  @Builder
  public ReservationEntity(Integer userCount, LocalDate startDate, LocalDate endDate, Integer pricePerNight,
      Long userId, Long roomId) {
    this.userCount = userCount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalPrice = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate) * pricePerNight);
    this.userId = userId;
    this.roomId = roomId;
  }
}
