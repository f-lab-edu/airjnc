package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationEntity;

public interface ReservationRepository {

  ReservationEntity findById(Long reservationId);

  void cancel(Long reservationId);

  void cancelDate(Long reservationId);
}
