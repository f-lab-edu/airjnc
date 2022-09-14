package com.airjnc.reservation.dao.impl;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.dto.response.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisReservationRepository implements ReservationRepository {

  private final ReservationMapper reservationMapper;

  private final CommonValidateService commonValidateService;


  @Override
  public List<ReservationDate> findAllByDate(Long roomId, List<LocalDate> dates) {
    return reservationMapper.findAllByDate(roomId, dates);
  }
}
