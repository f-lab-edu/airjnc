package com.airjnc.admin.dao;

import com.airjnc.room.domain.RoomStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

  int updateRoom(@Param("id") Long id, @Param("status") RoomStatus status);
}

