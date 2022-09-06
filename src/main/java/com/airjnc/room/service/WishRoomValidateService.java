package com.airjnc.room.service;

import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.exception.AlreadyWishRoomException;
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

}
