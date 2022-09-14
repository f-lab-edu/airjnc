package com.airjnc.reservation.service;

import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final ReservationValidateService reservationValidateService;

  @Transactional
  public void cancel(Long userId, Long reservationId) {
    ReservationEntity reservation = reservationRepository.findById(reservationId);
    reservationValidateService.shouldBeIsMine(reservation, userId);
    reservationValidateService.nowShouldBeBeforeStartDate(reservation);
    reservationRepository.cancel(reservationId);
    reservationRepository.cancelDate(reservationId);
  }
}
