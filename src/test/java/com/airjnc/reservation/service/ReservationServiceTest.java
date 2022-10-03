package com.airjnc.reservation.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.service.RoomService;
import com.testutil.annotation.UnitTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class ReservationServiceTest {

  @Mock
  RoomService roomService;

  @Mock
  ReservationRepository reservationRepository;

  @Mock
  ReservationValidateService reservationValidateService;

  @InjectMocks
  ReservationService reservationService;

  @Test()
  void reservation() {
    Long userId = 1L;
    Long roomId = 1L;
    ReservationReq req = ReservationReq.builder()
        .startDate(LocalDate.parse("2022-01-01")).endDate(LocalDate.parse("2022-01-03")).guestCount(5).build();
    Room room = Room.builder().minNumberOfNights(1).maxNumberOfNights(3).pricePerNight(1000).build();
    given(roomService.getRoomById(roomId)).willReturn(room);
    //when
    reservationService.reservation(userId, roomId, req);
    //then
    then(roomService).should().getRoomById(roomId);
    then(reservationValidateService).should()
        .checkReservationBetween(room.getMinNumberOfNights(), room.getMaxNumberOfNights(), req.getStartDate(),
            req.getEndDate());
    then(reservationValidateService).should().guestCountShouldNotBeExceed(room, req.getGuestCount());
    then(reservationValidateService).should().roomCountShouldNotBeExceed(room, req.getStartDate(), req.getEndDate());
    then(reservationRepository).should().createReservation(any(ReservationEntity.class));
    then(reservationRepository).should().createReservationDate(anyList());
  }
}
