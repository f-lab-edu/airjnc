package com.airjnc.reservation.service;

import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.response.Reservation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  private final ReservationValidateService reservationValidateService;

  @Transactional
  public void cancel(Long userId, Long reservationId) {
    ReservationEntity reservation = reservationRepository.findById(reservationId);
    reservationValidateService.shouldBeIsMine(reservation, userId);
    reservationValidateService.nowShouldBeBeforeStartDate(reservation);
    reservationRepository.cancel(reservationId);
    reservationRepository.cancelDate(reservationId);
  }

  public Page<Reservation> getAll(Long userId, Pageable pageable) {
    List<Reservation> list = reservationRepository.findAllByUserId(userId, pageable.getOffset(),
        pageable.getOffset() - pageable.getPageSize());
    return new PageImpl<>(list, pageable, reservationRepository.count(userId));
  }
}
