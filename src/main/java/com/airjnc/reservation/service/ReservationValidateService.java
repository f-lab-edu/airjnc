package com.airjnc.reservation.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.dto.ReservationDate;
import com.airjnc.reservation.exception.OverGuestCountException;
import com.airjnc.reservation.exception.OverRoomCountException;
import com.airjnc.reservation.exception.ReservationBetweenException;
import com.airjnc.room.dto.response.Room;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationValidateService {

  private final ReservationRepository reservationRepository;

  public void checkReservationBetween(int minNumberOfNights, int maxNumberOfNights, long between) {
    if (between >= minNumberOfNights && between <= maxNumberOfNights) {
      return;
    }
    throw new ReservationBetweenException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "reservationBetween",
            new Object[]{minNumberOfNights, maxNumberOfNights, between})
    );
  }

  public void guestCountShouldNotBeExceed(Room room, int reqGuestCount) {
    if (room.getMaxGuestCount() >= reqGuestCount) {
      return;
    }
    throw new OverGuestCountException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "guestCountIsExceed",
            new Object[]{room.getMaxGuestCount(), reqGuestCount}));
  }

  public void roomCountShouldNotBeExceed(Room room, LocalDate startDate, LocalDate endDate) {
    List<ReservationDate> allByDate = reservationRepository.findAllByDate(room.getId(), startDate, endDate);
    for (ReservationDate reservationDate : allByDate) {
      if (reservationDate.getCount() + 1 > room.getRoomCount()) {
        throw new OverRoomCountException(
            ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "roomCountIsExceed",
                new Object[]{reservationDate.getDate(), reservationDate.getCount()}));
      }
    }
  }
}
