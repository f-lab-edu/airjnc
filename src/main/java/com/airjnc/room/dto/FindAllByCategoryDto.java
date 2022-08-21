package com.airjnc.room.dto;

import com.airjnc.room.domain.RoomStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAllByCategoryDto {

  private final Long categoryId;

  private final RoomStatus roomStatus;

  private final Long skip;

  private final Long offset;

  @Builder
  public FindAllByCategoryDto(Long categoryId, RoomStatus roomStatus, Long offset, Long skip) {
    this.categoryId = categoryId;
    this.roomStatus = roomStatus;
    this.offset = offset;
    this.skip = skip;
  }
}
