package com.airjnc.reservation.dao;

import com.airjnc.reservation.dto.response.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReservationRepository {

  List<ReservationDate> findAllByDate(@Param("roomId") Long roomId, @Param("date") List<LocalDate> dates);
}
