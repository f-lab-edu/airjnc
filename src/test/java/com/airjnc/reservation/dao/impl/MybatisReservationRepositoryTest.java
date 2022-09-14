package com.airjnc.reservation.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.reservation.dao.ReservationMapper;
import com.airjnc.reservation.dao.ReservationRepository;
import com.airjnc.reservation.domain.ReservationEntity;
import com.airjnc.reservation.dto.response.Reservation;
import com.testutil.annotation.MybatisTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
  void cancel() {
    Long reservationId = 1L;
    // 정상적으로 동작하지 않으면 내부에서 Exception을 던지기 때문에, assertThat을 사용하지 않고도 정상 동작 확인 가능
    reservationRepository.cancel(reservationId);
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
}
