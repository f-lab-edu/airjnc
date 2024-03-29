package com.airjnc.reservation.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import com.airjnc.reservation.exception.*;
import com.airjnc.room.dto.response.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationValidateService {


  private final ReservationRepository reservationRepository;

  public void shouldBeIsMine(ReservationEntity reservation, Long userId) {
    if (reservation.getUserId().equals(userId)) {
      return;
    }
    throw new ReservationIsNotMineException(
            ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "shouldBeIsMine")
    );
  }

  public void nowShouldBeBeforeStartDate(ReservationEntity reservation) {
    if (LocalDate.now().isBefore(reservation.getStartDate())) {
      return;
    }
    throw new NowIsAfterStartDateException(
            ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "nowShouldBeBeforeStartDate")
    );
  }

  public void checkReservationBetween(int minNumberOfNights, int maxNumberOfNights, LocalDate startDate,
                                      LocalDate endDate) {
    long between = ChronoUnit.DAYS.between(startDate, endDate);
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
    List<ReservationDate> allByDate = reservationRepository.findAllByDateWithLock(room.getId(), startDate, endDate);
    for (ReservationDate reservationDate : allByDate) {
      if (reservationDate.getCount() + 1 > room.getRoomCount()) {
        throw new OverRoomCountException(
                ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "roomCountIsExceed",
                        new Object[]{reservationDate.getDate(), reservationDate.getCount()}));
      }
    }
  }
}
