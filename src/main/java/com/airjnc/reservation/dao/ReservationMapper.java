package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.response.Reservation;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {

  Optional<ReservationEntity> findById(Long reservationId);

  int cancel(Long reservationId);

  int cancelDate(Long reservationId);

  List<Reservation> findAllByUserId(@Param("userId") Long userId, @Param("offset") long offset,
      @Param("skip") long skip);

  int count(Long userId);
}
