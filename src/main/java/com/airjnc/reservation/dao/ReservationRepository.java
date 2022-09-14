package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.response.Reservation;
import java.util.List;

public interface ReservationRepository {

  ReservationEntity findById(Long reservationId);

  void cancel(Long reservationId);

  void cancelDate(Long reservationId);

  List<Reservation> findAllByUserId(Long userId, long offset, long skip);

  int count(Long userId);
}
