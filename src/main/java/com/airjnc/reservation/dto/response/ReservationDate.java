package com.airjnc.reservation.dto.response;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ReservationDate {

  LocalDate date;

  Integer count;
}
