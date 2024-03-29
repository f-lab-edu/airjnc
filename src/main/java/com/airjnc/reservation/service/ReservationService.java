package com.airjnc.reservation.service;

import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.reservation.dto.response.Reservation;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final RoomService roomService;

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

  public void reservation(Long userId, Long roomId, ReservationReq req) {
    Room room = roomService.getRoomById(roomId);
    // check reservation date [최소/최대 숙박 기간 내로 예약해야 한다.]
    reservationValidateService.checkReservationBetween(
            room.getMinNumberOfNights(), room.getMaxNumberOfNights(),
            req.getStartDate(), req.getEndDate()
    );
    // check max_guest_count [최대 예약 명수 내로 예약해야 한다.]
    reservationValidateService.guestCountShouldNotBeExceed(room, req.getGuestCount());
    // check room_count [현재 방이 남아 있을 경우에만 예약이 가능하다.]
    reservationValidateService.roomCountShouldNotBeExceed(room, req.getStartDate(), req.getEndDate());
    // reservation
    ReservationEntity reservation = req.toReservationEntity(userId, roomId, room.getPricePerNight());
    reservationRepository.createReservation(reservation);
    reservationRepository.createReservationDate(req.toReservationEntityList(roomId, reservation.getId()));
  }
}
