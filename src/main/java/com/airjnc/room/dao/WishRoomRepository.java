package com.airjnc.room.dao;

public interface WishRoomRepository {

  int count(Long userId);

  void create(Long userId, Long roomId);

  boolean existsByUserIdAndRoomId(Long userId, Long roomId);
}
