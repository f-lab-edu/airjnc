package com.airjnc.room.service;

import com.airjnc.room.dao.WishRoomRepository;
import lombok.RequiredArgsConstructor;
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

  public void delete(Long userId, Long id) {
    wishRoomValidateService.shouldBeWishRoom(userId, id);
    wishRoomRepository.delete(id);
  }
}
