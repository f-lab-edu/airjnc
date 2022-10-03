package com.airjnc.reservation.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
    reservationMapper.cancel(reservationId);
  }

  @Override
  public List<ReservationDate> findAllByDateWithLock(Long roomId, LocalDate startDate, LocalDate endDate) {
    return reservationMapper.findAllByDateWithLock(roomId, startDate, endDate);
  }

  @Override
  public void createReservation(ReservationEntity reservation) {
    int affect = reservationMapper.createReservation(reservation);
    commonValidateService.shouldBeMatch(affect, 1);
  }

  @Override
  public void cancelDate(Long reservationId) {
    int affect = reservationMapper.cancelDate(reservationId);
    commonValidateService.shouldNotBeMatch(affect, 0); // 0만 아니면 됨
  }

  public void createReservationDate(List<ReservationDateEntity> list) {
    int affect = reservationMapper.createReservationDate(list);
    commonValidateService.shouldBeMatch(affect, list.size());
  }
}
