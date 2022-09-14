package com.airjnc.reservation.dao.impl;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
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
  public List<ReservationDate> findAllByDate(Long roomId, LocalDate startDate, LocalDate endDate) {
    return reservationMapper.findAllByDate(roomId, startDate, endDate);
  }

  @Override
  public void createReservation(ReservationEntity reservation) {
    int affect = reservationMapper.createReservation(reservation);
    commonValidateService.shouldBeMatch(affect, 1);
  }

  @Override
  public void createReservationDate(List<ReservationDateEntity> list) {
    int affect = reservationMapper.createReservationDate(list);
    commonValidateService.shouldBeMatch(affect, list.size());
  }
}
