package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {

  List<ReservationDate> findAllByDate(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  int createReservation(ReservationEntity reservation);

  int createReservationDate(@Param("dates") List<ReservationDateEntity> list);
}
