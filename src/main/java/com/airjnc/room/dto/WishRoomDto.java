package com.airjnc.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WishRoomDto {

  private final Long id;

  private final Long userId;

  private final Long roomId;

  @Builder
  public WishRoomDto(Long id, Long userId, Long roomId) {
    this.id = id;
    this.userId = userId;
    this.roomId = roomId;
  }
}
