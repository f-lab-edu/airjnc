package com.airjnc.reservation.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Reservation {

  private Long id;

  private Integer userCount;

  private LocalDate startDate;

  private LocalDate endDate;

  private Integer totalPrice;

  private Long userId;

  private Long roomId;
}
