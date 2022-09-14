package com.airjnc.reservation.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.exception.NowIsAfterStartDateException;
import com.airjnc.reservation.exception.ReservationIsNotMineException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class ReservationValidateService {


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
}
