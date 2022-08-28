package com.airjnc.room.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.room.dao.WishRoomMapper;
import com.airjnc.room.dao.WishRoomRepository;
import com.testutil.annotation.IntegrationTest;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestId;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@IntegrationTest
class MybatisWishRoomRepositoryTest {

  @Autowired
  WishRoomMapper wishRoomMapper;

  @Mock
  CommonValidateService commonValidateService;

  WishRoomRepository wishRoomRepository;

  @BeforeEach
  void beforeEach() {
    wishRoomRepository = new MybatisWishRoomRepository(wishRoomMapper, commonValidateService);
  }

  @Test
  public void count() {
    //when
    int count = wishRoomRepository.count(TestId.USER);
    //then
    assertThat(count).isEqualTo(1);
  }

  @Test
  @Transactional
  void create() {
    //given
    Long newRoomId = TestId.WISH_ROOM + 1L;
    //when
    wishRoomRepository.create(TestUser.ID, newRoomId);
    boolean exists = wishRoomRepository.existsByUserIdAndRoomId(TestUser.ID, newRoomId);
    //then
    assertThat(exists).isTrue();
  }

  @Test
  void exists() {
    //when
    boolean exists1 = wishRoomRepository.existsByUserIdAndRoomId(TestUser.ID, TestId.ROOM[0]);
    boolean exists2 = wishRoomRepository.existsByUserIdAndRoomId(TestUser.ID, TestId.ROOM[1]);
    //then
    assertThat(exists1).isTrue();
    assertThat(exists2).isFalse();
  }
}
