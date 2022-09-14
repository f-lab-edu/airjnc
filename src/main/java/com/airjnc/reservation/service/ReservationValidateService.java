package com.airjnc.reservation.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.dto.response.ReservationDate;
import com.airjnc.reservation.exception.OverGuestCountException;
import com.airjnc.reservation.exception.OverRoomCountException;
import com.airjnc.room.dto.response.Room;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationValidateService {

  private final ReservationRepository reservationRepository;

  public void guestCountShouldNotBeExceed(Room room, int reqGuestCount) {
    if (room.getMaxGuestCount() >= reqGuestCount) {
      return;
    }
    throw new OverGuestCountException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "GuestCountIsExceed"));
  }

  public void roomCountShouldNotBeExceed(Room room, List<LocalDate> dates) {
    List<ReservationDate> allByDate = reservationRepository.findAllByDate(room.getId(), dates);
    for (ReservationDate reservationDate : allByDate) {
      if (reservationDate.getCount() + 1 > room.getRoomCount()) {
        throw new OverRoomCountException(
            ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "RoomCountIsExceed"));
      }
    }
  }
}
