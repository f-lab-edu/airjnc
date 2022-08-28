package com.airjnc.room.dao.impl;

import com.airjnc.common.service.CommonValidateService;
import com.airjnc.room.dao.WishRoomMapper;
import com.airjnc.room.dao.WishRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisWishRoomRepository implements WishRoomRepository {

  private final WishRoomMapper wishRoomMapper;

  private final CommonValidateService commonValidateService;

  @Override
  public int count(Long userId) {
    return wishRoomMapper.count(userId);
  }

  @Override
  public void create(Long userId, Long roomId) {
    int affect = wishRoomMapper.create(userId, roomId);
    commonValidateService.shouldBeMatch(affect, 1);
  }

  @Override
  public boolean existsByUserIdAndRoomId(Long userId, Long roomId) {
    return wishRoomMapper.existsByUserIdAndRoomId(userId, roomId);
  }
}
