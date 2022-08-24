package com.airjnc.room.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.SimpleRoom;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.room.RoomGetAllReqFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    Pageable pageable = PageRequest.of(1, 20);
    List<SimpleRoom> list = List.of(new SimpleRoom());
    given(roomRepository.findAllByCategory(any(FindAllByCategoryDto.class))).willReturn(list);
    //when
    Page<SimpleRoom> result = roomService.getAll(req, pageable);
    //then
    then(roomRepository).should().findAllByCategory(any(FindAllByCategoryDto.class));
  }
}
