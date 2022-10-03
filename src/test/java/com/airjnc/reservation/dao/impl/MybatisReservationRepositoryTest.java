package com.airjnc.reservation.dao.impl;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationDateEntity;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.ReservationDate;
import com.airjnc.reservation.dto.request.ReservationReq;
import com.airjnc.reservation.dto.response.Reservation;
import com.testutil.annotation.MybatisTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class MybatisReservationRepositoryTest {

  ReservationRepository reservationRepository;

  @Mock
  CommonValidateService commonValidateService;

  @Autowired
  ReservationMapper reservationMapper;

  @BeforeEach
  void beforeEach() {
    reservationRepository = new MybatisReservationRepository(reservationMapper, commonValidateService);
  }

  @Test
  void findById() {
    Long reservationId = 1L;
    ReservationEntity reservation = reservationRepository.findById(reservationId);
    assertThat(reservation.getId()).isEqualTo(reservationId);
  }

  @Test
  @Transactional
  void findAllByDate() {
    Long roomId = 1L;
    List<ReservationDate> allByDate = reservationRepository.findAllByDateWithLock(roomId, LocalDate.parse("2022-01-01"),
            LocalDate.parse("2022-01-02"));

    assertThat(allByDate.size()).isEqualTo(2);
    for (ReservationDate reservationDate : allByDate) {
      assertThat(reservationDate.getCount()).isEqualTo(1);
    }
  }

  @Test
  @Transactional
  void cancel() {
    Long reservationId = 1L;
    // 정상적으로 동작하지 않으면 내부에서 Exception을 던지기 때문에, assertThat을 사용하지 않고도 정상 동작 확인 가능
    reservationRepository.cancel(reservationId);
  }

  @Test
  @Transactional
  void createReservation() {
    LocalDate s = LocalDate.parse("2022-02-10");
    LocalDate e = LocalDate.parse("2022-02-14");
    ReservationEntity reservation = ReservationEntity.builder()
            .userCount(5)
            .startDate(s)
            .endDate(e)
            .pricePerNight(1000)
            .userId(1L)
            .roomId(1L)
            .build();
    reservationRepository.createReservation(reservation);
  }

  @Test
  @Transactional
  void cancelDate() {
    Long reservaionId = 1L;
    reservationRepository.cancelDate(reservaionId);
  }

  @Test
  void findAllByUser() {
    Long userId = 1L;
    long offset = 10L;
    long skip = 0;
    List<Reservation> list = reservationMapper.findAllByUserId(userId, offset, skip);
    assertThat(list.size()).isNotEqualTo(0);
    for (Reservation reservation : list) {
      assertThat(reservation.getUserId()).isEqualTo(userId);
    }
  }

  @Test
  void count() {
    int count = reservationMapper.count(1L);
    assertThat(count).isNotEqualTo(0);
  }

  @Transactional
  void createReservationDate() {
    Long roomId = 1L;
    Long roomReservationId = 1L;
    List<ReservationDateEntity> reservationDateEntities = ReservationReq.builder()
            .startDate(LocalDate.parse("2022-01-01"))
            .endDate(LocalDate.parse("2022-01-02"))
            .guestCount(5)
            .build()
            .toReservationEntityList(roomId, roomReservationId);
    reservationRepository.createReservationDate(reservationDateEntities);

    for (ReservationDateEntity reservationDateEntity : reservationDateEntities) {
      assertThat(reservationDateEntity.getId()).isNotNull();
    }
  }
}
