package com.airjnc.reservation.dao;

import com.airjnc.reservation.dto.response.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReservationMapper {

  List<ReservationDate> findAllByDate(@Param("roomId") Long roomId, @Param("dates") List<LocalDate> dates);
}
