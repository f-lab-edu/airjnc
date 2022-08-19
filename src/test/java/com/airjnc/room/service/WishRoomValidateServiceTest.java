package com.airjnc.room.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.WishRoomDto;
import com.airjnc.room.exception.AlreadyWishRoomException;
import com.airjnc.room.exception.NotWishRoomException;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestId;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void givenReturnFalse_ThenNotThrow() {
      //given
      Long roomId = 1L;
      given(wishRoomRepository.existsByUserIdAndRoomId(anyLong(), anyLong())).willReturn(false);
      //when
      wishRoomValidateService.shouldBeNotWishRoom(TestUser.ID, roomId);
    }

    @Test
    void givenReturnTrue_ThenThrow() {
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

  @Nested
  class ShouldBeWishRoom {

    @Test
    void givenEqualsUserId_thenThrow() {
      //given
      WishRoomDto dto = WishRoomDto.builder().userId(TestUser.ID).build();
      given(wishRoomRepository.findById(anyLong())).willReturn(dto);
      //when
      wishRoomValidateService.shouldBeWishRoom(TestUser.ID, TestId.WISH_ROOM);
    }

    @Test
    void givenNotEqualsUserId_thenThrow() {
      //given
      WishRoomDto dto = WishRoomDto.builder().userId(TestUser.ID + 1L).build();
      given(wishRoomRepository.findById(anyLong())).willReturn(dto);
      //when
      assertThrows(
          NotWishRoomException.class,
          () -> wishRoomValidateService.shouldBeWishRoom(TestUser.ID, TestId.WISH_ROOM)
      );
    }

  }
}
