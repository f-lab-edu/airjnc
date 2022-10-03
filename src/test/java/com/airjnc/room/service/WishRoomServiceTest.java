package com.airjnc.room.service;

import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.response.SimpleRoom;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestId;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTest
@ExtendWith(MockitoExtension.class)
class WishRoomServiceTest {

  @Mock
  WishRoomRepository wishRoomRepository;

  @Mock
  WishRoomValidateService wishRoomValidateService;

  @Mock
  RoomRepository roomRepository;

  @InjectMocks
  WishRoomService wishRoomService;

  @Test
  void create() {
    //given
    Long roomId = 1L;
    //when
    wishRoomService.create(TestUser.ID, roomId);
    //then
    then(wishRoomValidateService).should().noAlreadyWishRoomElseThrow(TestUser.ID, roomId);
    then(wishRoomRepository).should().create(TestUser.ID, roomId);
  }

  @Test
  public void getMyWishRooms() {
    //given
    Pageable pageable = PageRequest.of(1, 20);
    Long skip = pageable.getOffset() - pageable.getPageSize();
    Long userId = TestUser.ID;
    List<SimpleRoom> list = List.of(new SimpleRoom());
    given(roomRepository.findAllByUserId(anyLong(), anyLong(), anyLong())).willReturn(list);
    //when
    Page<SimpleRoom> page = wishRoomService.getMyWishRooms(userId, pageable);
    //then
    then(roomRepository).should().findAllByUserId(userId, skip, pageable.getOffset());
  }

  @Test
  void delete() {
    //given
    Long wishRoomId = TestId.WISH_ROOM;
    //when
    wishRoomService.delete(TestUser.ID, wishRoomId);
    //then
    then(wishRoomValidateService).should().shouldAlreadyBeWishRoomElseThrow(TestUser.ID, wishRoomId);
    then(wishRoomRepository).should().delete(wishRoomId);
  }
}
