package com.airjnc.room.service;

import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.response.SimpleRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishRoomService {

  private final WishRoomRepository wishRoomRepository;

  private final WishRoomValidateService wishRoomValidateService;

  public void create(Long userId, Long roomId) {
    wishRoomValidateService.shouldBeNotWishRoom(userId, roomId);
    wishRoomRepository.create(userId, roomId);
  }

  public Page<SimpleRoom> getMyWishRooms(Long userId, Pageable pageable) {
    return null;
  }
}
