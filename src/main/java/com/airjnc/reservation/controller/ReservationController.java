package com.airjnc.reservation.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @DeleteMapping("/{reservationId}")
  @CheckAuth
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancel(@CurrentUserId Long userId, @PathVariable Long reservationId) {
    reservationService.cancel(userId, reservationId);
  }
}
