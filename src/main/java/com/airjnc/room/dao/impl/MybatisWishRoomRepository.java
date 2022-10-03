package com.airjnc.room.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.room.dao.WishRoomMapper;
import com.airjnc.room.dao.WishRoomRepository;
import com.airjnc.room.dto.WishRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisWishRoomRepository implements WishRoomRepository {

  private final WishRoomMapper wishRoomMapper;

  private final CommonValidateService commonValidateService;

  @Override
  public void create(Long userId, Long roomId) {
    int affect = wishRoomMapper.create(userId, roomId);
    commonValidateService.shouldBeMatch(affect, 1);
  }

  @Override
  public void delete(Long id) {
    int affect = wishRoomMapper.delete(id);
    commonValidateService.shouldBeMatch(affect, 1);
  }


  @Override
  public boolean existsByUserIdAndRoomId(Long userId, Long roomId) {
    return wishRoomMapper.existsByUserIdAndRoomId(userId, roomId);
  }

  @Override
  public WishRoomDto findById(Long id) {
    return wishRoomMapper.findById(id).orElseThrow(NotFoundException::new);
  }
}
