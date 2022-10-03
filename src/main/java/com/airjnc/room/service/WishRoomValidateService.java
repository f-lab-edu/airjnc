package com.airjnc.room.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.WishRoomDto;
import com.airjnc.room.exception.AlreadyWishRoomException;
import com.airjnc.room.exception.NotWishRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishRoomValidateService {

  private final WishRoomRepository wishRoomRepository;

  public void shouldBeNotWishRoom(Long userId, Long roomId) {
    boolean exists = wishRoomRepository.existsByUserIdAndRoomId(userId, roomId);
    if (!exists) {
      return;
    }
    throw new AlreadyWishRoomException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "alreadyWishRoom")
    );
  }

  public void shouldBeWishRoom(Long userId, Long id) {
    WishRoomDto dto = wishRoomRepository.findById(id);
    if (dto.getUserId().equals(userId)) {
      return;
    }
    throw new NotWishRoomException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "notWishRoom")
    );
  }
}
