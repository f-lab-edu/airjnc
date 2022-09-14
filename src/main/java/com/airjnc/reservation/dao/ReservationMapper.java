package com.airjnc.reservation.dao;

import com.airjnc.reservation.domain.ReservationEntity;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {

  Optional<ReservationEntity> findById(Long reservationId);

  int cancel(Long reservationId);

  int cancelDate(Long reservationId);
}
