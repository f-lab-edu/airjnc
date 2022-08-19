package com.airjnc.room.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.RoomSimpleResp;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.room.RoomGetAllReqFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

  @Mock
  RoomRepository roomRepository;

  @InjectMocks
  RoomService roomService;

  @Test
  void getAll() {
    RoomGetAllReq req = RoomGetAllReqFixture.getBuilder().build();
    Pageable pageable = Pageable.builder().skip(0).take(20).build();
    List<RoomSimpleResp> list = List.of(new RoomSimpleResp());
    given(roomRepository.findAllByCategory(anyLong(), any(RoomStatus.class), any(Pageable.class))).willReturn(list);
    //when
    List<RoomSimpleResp> result = roomService.getAll(req, pageable);
    //then
    then(roomRepository).should().findAllByCategory(req.getCategoryId(), req.getStatus(), pageable);
    assertThat(list).isEqualTo(result);
  }
}
