package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

  List<ReservationDate> findAllByDate(Long roomId, LocalDate startDate, LocalDate endDate);

  void createReservation(ReservationEntity reservation);

  void createReservationDate(List<ReservationDateEntity> list);
}
