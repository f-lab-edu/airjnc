package com.airjnc.room.dao;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.RoomSimpleResp;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoomMapper {

  List<RoomSimpleResp> findAllByCategory(@Param("categoryId") Long categoryId,
      @Param("roomStatus") RoomStatus roomStatus, @Param("pageable") Pageable pageable);
}
