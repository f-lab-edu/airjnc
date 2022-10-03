package com.airjnc.room.service;

import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.WishRoomDto;
import com.airjnc.room.exception.AlreadyWishRoomException;
import com.airjnc.room.exception.NotWishRoomException;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestId;
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
      wishRoomValidateService.noAlreadyWishRoomElseThrow(TestUser.ID, roomId);
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
              () -> wishRoomValidateService.noAlreadyWishRoomElseThrow(TestUser.ID, roomId)
      );
    }

  }

  @Nested
  class ShouldBeWishRoom {

    @Test
    @DisplayName("위시 리스트에 있는 방이라면, 성공적으로 반환된다.")
    void givenAlreadyWishRoom_ThenSuccess() {
      //given
      WishRoomDto dto = WishRoomDto.builder().userId(TestUser.ID).build();
      given(wishRoomRepository.findById(anyLong())).willReturn(dto);
      //when
      wishRoomValidateService.shouldAlreadyBeWishRoomElseThrow(TestUser.ID, TestId.WISH_ROOM);
    }

    @Test
    @DisplayName("위시 리스트에 있는 방이 아니라면, NotWishRoomException을 던져야 한다.")
    void givenNotEqualsUserId_thenThrow() {
      //given
      WishRoomDto dto = WishRoomDto.builder().userId(TestUser.ID + 1L).build();
      given(wishRoomRepository.findById(anyLong())).willReturn(dto);
      //when
      assertThrows(
              NotWishRoomException.class,
              () -> wishRoomValidateService.shouldAlreadyBeWishRoomElseThrow(TestUser.ID, TestId.WISH_ROOM)
      );
    }

  }
}
