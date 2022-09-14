package com.airjnc.reservation.service;

import com.airjnc.reservation.dao.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public void cancel(Long userId, Long reservationId) {

  }
}
