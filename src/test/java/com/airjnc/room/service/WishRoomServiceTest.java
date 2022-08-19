package com.airjnc.room.service;

import static org.mockito.BDDMockito.then;

import com.airjnc.room.dao.WishRoomRepository;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class WishRoomServiceTest {

  @Mock
  WishRoomRepository wishRoomRepository;

  @Mock
  WishRoomValidateService wishRoomValidateService;

  @InjectMocks
  WishRoomService wishRoomService;

  @Test
  void create() {
    //given
    Long roomId = 1L;
    //when
    wishRoomService.create(TestUser.ID, roomId);
    //then
    then(wishRoomValidateService).should().shouldBeNotWishRoom(TestUser.ID, roomId);
    then(wishRoomRepository).should().create(TestUser.ID, roomId);
  }
}
