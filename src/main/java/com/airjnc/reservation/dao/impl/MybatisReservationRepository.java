package com.airjnc.reservation.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisReservationRepository implements ReservationRepository {

  private final ReservationMapper reservationMapper;

  private final CommonValidateService commonValidateService;

  @Override
  public ReservationEntity findById(Long reservationId) {
    return reservationMapper.findById(reservationId).orElseThrow(NotFoundException::new);
  }

  @Override
  public void cancel(Long reservationId) {
    int affect = reservationMapper.cancel(reservationId);
    commonValidateService.shouldBeMatch(affect, 1);
  }

  @Override
  public void cacnelDate(Long reservationId) {
    int affect = reservationMapper.cacnelDate(reservationId);
    commonValidateService.shouldBeMatch(affect, 1);
  }
}
