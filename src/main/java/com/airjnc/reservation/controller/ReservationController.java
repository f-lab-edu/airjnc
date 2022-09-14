package com.airjnc.reservation.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/{roomId}")
  @CheckAuth
  @ResponseStatus(HttpStatus.CREATED)
  public void reservation(@CurrentUserId Long userId, @PathVariable Long roomId,
      @Validated @RequestBody ReservationReq req) {
    reservationService.reservation(userId, roomId, req);
  }
}
