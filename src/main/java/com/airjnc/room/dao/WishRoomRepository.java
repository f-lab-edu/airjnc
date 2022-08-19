package com.airjnc.room.dao;

public interface WishRoomRepository {

  void create(Long userId, Long roomId);

  boolean existsByUserIdAndRoomId(Long userId, Long roomId);
}
