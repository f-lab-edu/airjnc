package com.airjnc.room.dao;

import com.airjnc.room.dto.WishRoomDto;

public interface WishRoomRepository {

  int count(Long userId);

  void create(Long userId, Long roomId);

  void delete(Long id);

  boolean existsByUserIdAndRoomId(Long userId, Long roomId);

  WishRoomDto findById(Long id);
}
