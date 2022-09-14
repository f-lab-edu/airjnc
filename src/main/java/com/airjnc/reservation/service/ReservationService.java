package com.airjnc.reservation.service;

import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final RoomService roomService;

  private final ReservationRepository reservationRepository;

  private final ReservationValidateService reservationValidateService;

  @Transactional
  public void reservation(Long userId, Long roomId, ReservationReq req) {
    Room room = roomService.getRoomById(roomId);
    // check max_guest_count
    reservationValidateService.guestCountShouldNotBeExceed(room, req.getGuestCount());
    // check room_count
    reservationValidateService.roomCountShouldNotBeExceed(room, req.dateToList());
    // reservation
  }
}
