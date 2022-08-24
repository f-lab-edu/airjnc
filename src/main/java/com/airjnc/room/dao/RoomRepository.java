package com.airjnc.room.dao;

import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomRepository {

  int count(@Param("categoryId") Long categoryId, @Param("status") RoomStatus status);

  List<SimpleRoom> findAllByCategory(FindAllByCategoryDto dto);
}
