package com.airjnc.room.service;

import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.exception.AlreadyWishRoomException;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@UnitTest
@ExtendWith(MockitoExtension.class)
class WishRoomValidateServiceTest {

  @Mock
  WishRoomRepository wishRoomRepository;

  @InjectMocks
  WishRoomValidateService wishRoomValidateService;

  @Nested
  class ShouldBeNotWishRoom {

    @Test
    @DisplayName("위시 리스트에 존재하지 않는 방이라면, 정상적으로 동작해야 한다.")
    void givenNoWishRoomThenSuccess() {
      //given
      Long roomId = 1L;
      given(wishRoomRepository.existsByUserIdAndRoomId(anyLong(), anyLong())).willReturn(false);
      //when
      wishRoomValidateService.shouldBeNotWishRoom(TestUser.ID, roomId);
    }

    @Test
    @DisplayName("이미 위시 리스트에 존재하는 방이라면, AlreadyWishRoomException을 던져야 한다.")
    void givenAlreadyWishRoomThenThrowException() {
      //given
      Long roomId = 1L;
      given(wishRoomRepository.existsByUserIdAndRoomId(anyLong(), anyLong())).willReturn(true);
      //when
      assertThrows(
              AlreadyWishRoomException.class,
              () -> wishRoomValidateService.shouldBeNotWishRoom(TestUser.ID, roomId)
      );
    }

  }

}
