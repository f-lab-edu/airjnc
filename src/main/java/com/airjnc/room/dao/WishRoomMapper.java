package com.airjnc.room.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WishRoomMapper {

  int create(@Param("userId") Long userId, @Param("roomId") Long roomId);

  boolean existsByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);
}
