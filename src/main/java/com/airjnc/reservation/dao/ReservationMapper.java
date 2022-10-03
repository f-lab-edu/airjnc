package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ReservationMapper {

  Optional<ReservationEntity> findById(Long reservationId);

  int cancel(Long reservationId);

  int cancelDate(Long reservationId);

  List<ReservationDate> findAllByDateWithLock(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

  int createReservation(ReservationEntity reservation);

  int createReservationDate(@Param("dates") List<ReservationDateEntity> list);
}
