package com.airjnc.reservation.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.response.Reservation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisReservationRepository implements ReservationRepository {

  private final ReservationMapper reservationMapper;

  private final CommonValidateService commonValidateService;

  @Override
  public List<Reservation> findAllByUserId(Long userId, long offset, long skip) {
    return reservationMapper.findAllByUserId(userId, offset, skip);
  }

  @Override
  public int count(Long userId) {
    return reservationMapper.count(userId);
  }

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
  public void cancelDate(Long reservationId) {
    int affect = reservationMapper.cancelDate(reservationId);
    commonValidateService.shouldNotBeMatch(affect, 0); // 0만 아니면 됨
  }
}
