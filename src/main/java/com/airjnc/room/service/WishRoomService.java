package com.airjnc.room.service;

import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.response.SimpleRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishRoomService {

  private final WishRoomRepository wishRoomRepository;

  private final WishRoomValidateService wishRoomValidateService;

  private final RoomRepository roomRepository;

  public void create(Long userId, Long roomId) {
    wishRoomValidateService.noAlreadyWishRoomElseThrow(userId, roomId);
    wishRoomRepository.create(userId, roomId);
  }

  public Page<SimpleRoom> getMyWishRooms(Long userId, Pageable pageable) {
    Long skip = pageable.getOffset() - pageable.getPageSize();
    List<SimpleRoom> list = roomRepository.findAllByUserId(userId, skip, pageable.getOffset());
    return new PageImpl<>(list, pageable, wishRoomRepository.count(userId));
  }

  public void delete(Long userId, Long wishRoomId) {
    wishRoomValidateService.shouldAlreadyBeWishRoomElseThrow(userId, wishRoomId);
    wishRoomRepository.delete(wishRoomId);
  }
}
